package com.ethan.gap.biz.service;

import java.util.Map;

import com.ethan.gap.dal.model.GapUserInfo;
import com.ethan.gap.dal.model.UserLoginInfo;

public interface UserInfoService {
	public int addUserInfo(GapUserInfo gapUserInfo);
	
	public UserLoginInfo queryUserInfo(Map<String, Object> queryMap);
}
