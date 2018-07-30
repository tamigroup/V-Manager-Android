package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 校验用户是否在群中.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CheckGroupUserRequestBean extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -3904886690078012439L;
	private int meetingId;
	private int userId;

	public CheckGroupUserRequestBean() {
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
		return HttpKey.CHECKGROUPUSER;
	}

	@Override
	public Class getResponseClass() {
		return CheckGroupUserResponseBean.class;
	}
}