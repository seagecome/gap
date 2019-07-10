package com.ethan.gap.api.dubbo;

import com.ethan.gap.api.dubbo.request.HelloWorldRequest;

public interface IHomeService {
	public String helloWorld(HelloWorldRequest request);
}
