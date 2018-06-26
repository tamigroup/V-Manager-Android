package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 校验用户是否在群中.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckGroupUserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String userId;

	public CheckGroupUserRequest() {
		super();
	}	

	/**
	 * @return 会议ID
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 用户ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}