package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 会议流程节点删除人员操作权限.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeleteMeetingItemsUserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingItemSetId;
	private String userId;

	public DeleteMeetingItemsUserRequest() {
		super();
	}	

	/**
	 * @return 会议节点ID
	 */
	public String getMeetingItemSetId() {
		return meetingItemSetId;
	}

	public void setMeetingItemSetId(String meetingItemSetId) {
		this.meetingItemSetId = meetingItemSetId;
	}

	/**
	 * @return 已分配权限的人员ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}