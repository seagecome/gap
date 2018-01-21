package com.ethan.gap.dal.dao.slave;

import java.util.Map;

import com.ethan.gap.dal.model.UserLoginInfo;

public interface SlaveQueryMapper {
	UserLoginInfo selectByPrimaryKey(Integer loginId);
	UserLoginInfo selectByMap(Map<String, Object> queryMap);
}
