package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 从会议分组中删除用户(退群).客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class OutGroupUserRequest implements Serializable{


	private static final long serialVersionUID = -1433931544545152804L;
	private String meetingId;
	private String userId;

	public OutGroupUserRequest() {
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