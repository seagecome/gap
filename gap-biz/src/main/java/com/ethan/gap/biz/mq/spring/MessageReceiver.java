package com.ethan.gap.biz.mq.spring;

import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Service("messageReceiver")
public class MessageReceiver implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("===receiver===got the message content : " + new String(message.getBody(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
