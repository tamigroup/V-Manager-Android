package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 添加用户到会议分组.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class IntoGroupUserRequestBean extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 7066091265531316236L;
	private int meetingId;
	private int userId;

	public IntoGroupUserRequestBean() {
		super();
	}	

	/**
	 * @return 会议ID
	 */
	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 用户ID
	 */
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.INTOGROUPUSER;
	}

	@Override
	public Class getResponseClass() {
		return IntoGroupUserResponseBean.class;
	}
}