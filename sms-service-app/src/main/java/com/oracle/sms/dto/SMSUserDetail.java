package com.oracle.sms.dto;

public class SMSUserDetail {
	private Long userId;
	private String userName;
	private String userRole;

	public SMSUserDetail(Long userId, String userName, String userRole) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userRole = userRole;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
