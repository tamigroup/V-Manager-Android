package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 添加用户到会议分组.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class IntoGroupUserRequest implements Serializable{


	private static final long serialVersionUID = 7066091265531316236L;
	private String meetingId;
	private String userId;

	public IntoGroupUserRequest() {
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