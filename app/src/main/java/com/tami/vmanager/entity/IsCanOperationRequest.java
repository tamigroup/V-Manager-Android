package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 验证用户是否已被分配了某节点权限.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class IsCanOperationRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String meetingItemId;
	private String userId;

	public IsCanOperationRequest() {
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
	 * @return 会议节点ID
	 */
	public String getMeetingItemId() {
		return meetingItemId;
	}

	public void setMeetingItemId(String meetingItemId) {
		this.meetingItemId = meetingItemId;
	}

	/**
	 * @return 人员ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}