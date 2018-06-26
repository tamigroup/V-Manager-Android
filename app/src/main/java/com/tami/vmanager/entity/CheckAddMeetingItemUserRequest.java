package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 判断用户是否有添加会议节点用户的权限.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckAddMeetingItemUserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingItemId;
	private String userId;

	public CheckAddMeetingItemUserRequest() {
		super();
	}	

	/**
	 * @return 会议节点ID
	 */
	public String getMeetingItemId() {
		return meetingItemId;
	}

	public void setMeetingItemId(String meetingItemId) {
		this.meetingItemId = meetingItemId;
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