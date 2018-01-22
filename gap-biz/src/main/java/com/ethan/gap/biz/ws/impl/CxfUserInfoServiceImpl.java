package com.ethan.gap.biz.ws.impl;

import javax.jws.WebService;

import org.springframework.stereotype.Component;

import com.ethan.gap.api.ws.CxfUserInfoSerivce;

@WebService(endpointInterface="com.ethan.gap.api.ws.CxfUserInfoSerivce",serviceName="cxfUserInfoSerivce")
@Component
public class CxfUserInfoServiceImpl implements CxfUserInfoSerivce {

	@Override
	public String showUserInfo(String userId) {
		//"查询数据库显示用户信息"
		return "姓名：张三；年龄30；职业:码农";
	}

}
