package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 取消关注某一个会议.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
@Deprecated
public class CancelUserMeetingRequest implements Serializable{

	private static final long serialVersionUID = -7225986451603363097L;
	private String meetingId;
	private String userId;

	public CancelUserMeetingRequest() {
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