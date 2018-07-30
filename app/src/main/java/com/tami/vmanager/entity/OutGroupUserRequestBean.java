package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 从会议分组中删除用户(退群).客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class OutGroupUserRequestBean extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -1433931544545152804L;
	private int meetingId;
	private int userId;

	public OutGroupUserRequestBean() {
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
		return HttpKey.OUTGROUPUSER;
	}

	@Override
	public Class getResponseClass() {
		return OutGroupUserResponseBean.class;
	}
}