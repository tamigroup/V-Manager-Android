package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 判断用户是否有添加会议节点用户的权限.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckAddMeetingItemUserRequest extends MobileMessage implements Serializable{

	private static final long serialVersionUID = 2366029242299662300L;

	public CheckAddMeetingItemUserRequest() {
		super();
	}

	private int userId;
	private int meetingItemSetId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMeetingItemSetId() {
		return meetingItemSetId;
	}

	public void setMeetingItemSetId(int meetingItemSetId) {
		this.meetingItemSetId = meetingItemSetId;
	}

	@Override
	public Class getResponseClass() {
		return CheckAddMeetingItemUserResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.CHECK_ADD_MEETING_ITEM_USER;
	}
}