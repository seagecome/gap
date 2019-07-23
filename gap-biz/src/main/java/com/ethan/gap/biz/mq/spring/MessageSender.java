package com.ethan.gap.biz.mq.spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ethan.gap.biz.mq.config.BizConfig;

@Service
public class MessageSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private BizConfig bizConfig;
	
	public void sendMsg() {
		rabbitTemplate.convertAndSend(bizConfig.getSettleDirectExchange(), bizConfig.getRoutingKey(), "wolai");
	}
}
