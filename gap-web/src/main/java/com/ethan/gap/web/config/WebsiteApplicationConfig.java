package com.ethan.gap.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebsiteApplicationConfig {
	@Value(value="${domain.host}")
	private String domainHost;

	public String getDomainHost() {
		return domainHost;
	}
	
}
