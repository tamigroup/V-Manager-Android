package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 取消会议.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeleteUserMeetingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String userId;

	public DeleteUserMeetingRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}