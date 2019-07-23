package com.ethan.gap.biz.mq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MessageReciver {
private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("172.30.16.43");
		factory.setUsername("admin");
		factory.setPassword("123456");
		factory.setVirtualHost("gap");
		
		try {
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			
			 DeliverCallback deliverCallback = (consumerTag, delivery) -> {
		            String message = new String(delivery.getBody(), "UTF-8");
		            System.out.println(" [x] Received '" + message + "'");
		        };
		        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
