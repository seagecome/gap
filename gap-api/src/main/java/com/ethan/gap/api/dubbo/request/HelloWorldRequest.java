package com.ethan.gap.api.dubbo.request;

import java.io.Serializable;

public class HelloWorldRequest implements Serializable {

	private static final long serialVersionUID = -8032195020828576821L;

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HelloWorldRequest [userName=").append(userName).append("]");
		return builder.toString();
	}
}
