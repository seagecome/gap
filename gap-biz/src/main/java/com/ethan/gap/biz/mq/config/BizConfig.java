package com.ethan.gap.biz.mq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BizConfig {
	@Value("${settle.direct.exchange}")
	private String settleDirectExchange;
	public String getSettleDirectExchange() {
		return settleDirectExchange;
	}
	
	@Value("${settle.routing.key}")
	private String routingKey;
	public String getRoutingKey() {
		return routingKey;
	}
	
}
