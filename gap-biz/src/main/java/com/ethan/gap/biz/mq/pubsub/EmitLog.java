package com.ethan.gap.biz.mq.pubsub;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {

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
			
			String message = args.length < 1?"info: Hello World!":String.join(" ", args);
			
			chnl.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			
			System.out.println(" [x] sent '"+message+"'");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
