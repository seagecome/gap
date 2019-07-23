package com.ethan.gap.biz.mq.route;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReceiveLogsDirect {
	private static final String EXCHANGE_NAME="direct_logs";
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("123456");
		factory.setHost("172.30.16.43");
		factory.setVirtualHost("gap");
		
		try {
			Connection conn = factory.newConnection();
			Channel chnl = conn.createChannel();
			chnl.exchangeDeclare(EXCHANGE_NAME, "direct");
			
			String queueName = chnl.queueDeclare().getQueue();
			if (args.length < 1) {
		        System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]");
		        System.exit(1);
		    }
			
			for(String severity : args) {
				chnl.queueBind(queueName, EXCHANGE_NAME, severity);
			}
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			
			DeliverCallback deliverCallback = (consumerTag, delivery)->{
				String message = new String(delivery.getBody(), "UTF-8");
				System.out.println(" [x] Received '" +
			            delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
			};
			
			chnl.basicConsume(queueName, true, deliverCallback, (consumerTag)->{});
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
