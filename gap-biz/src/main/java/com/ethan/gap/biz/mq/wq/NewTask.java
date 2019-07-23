package com.ethan.gap.biz.mq.wq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewTask {
	private final static String QUEUE_NAME="wkq";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("admin");
		factory.setPassword("123456");
		factory.setHost("172.30.16.43");
		factory.setVirtualHost("gap");
		
		try {
			
			Connection conn = factory.newConnection();
			Channel  channel = conn.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = String.join(" ", args);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println("===workqueue===" + message);
			
		} catch (Exception e) {
			
		} finally {
			
		}
	}
	
}
