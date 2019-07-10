package com.ethan.gap.web.dubbo.controller;

import org.springframework.stereotype.Service;

import com.ethan.gap.api.dubbo.IHomeService;
import com.ethan.gap.api.dubbo.request.HelloWorldRequest;

@Service("homeService")
public class HomeServiceImpl implements IHomeService {

	@Override
	public String helloWorld(HelloWorldRequest request) {
		return request.getUserName().concat(" said: hello world");
	}

}
