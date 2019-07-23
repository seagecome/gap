package com.ethan.gap.biz.mq.pubsub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RecvLogs {
	public static final String EXCHANGE_NAME="logs";
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("123456");
		factory.setHost("172.30.16.43");
		factory.setVirtualHost("gap");
		
		try {
			Connection conn = factory.newConnection();
			Channel chnl = conn.createChannel();
			chnl.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = chnl.queueDeclare().getQueue();
			chnl.queueBind(queueName, EXCHANGE_NAME, "");
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		        String message = new String(delivery.getBody(), "UTF-8");
		        System.out.println(" [x] Received '" + message + "'");
		    };
		    chnl.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
