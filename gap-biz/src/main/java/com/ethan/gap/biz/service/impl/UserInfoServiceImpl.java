package com.ethan.gap.biz.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ethan.gap.biz.service.UserInfoService;
import com.ethan.gap.dal.dao.master.GapUserInfoMapper;
import com.ethan.gap.dal.dao.slave.SlaveQueryMapper;
import com.ethan.gap.dal.model.GapUserInfo;
import com.ethan.gap.dal.model.UserLoginInfo;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private GapUserInfoMapper gapUserInfoMapper;
	@Autowired
	private SlaveQueryMapper slaveQueryMapper;
	
	@Override
	public int addUserInfo(GapUserInfo gapUserInfo) {
		return gapUserInfoMapper.insertSelective(gapUserInfo);
	}

	@Override
	public UserLoginInfo queryUserInfo(Map<String, Object> queryMap) {
		return  slaveQueryMapper.selectByMap(queryMap);
	}
	
}
