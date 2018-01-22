package com.ethan.gap.api.ws;

import javax.jws.WebService;

@WebService
public interface CxfUserInfoSerivce {
	String showUserInfo(String userId);
}
