package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 设置单个会议流程节点的状态.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SetMeetingItemsStatusRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int meetingItemSetId;
	private int status;//1代表通过  2代表有问题 3代表正常状态（灰色）
	private int userId;


	public SetMeetingItemsStatusRequest() {
		super();
	}

	public int getMeetingItemSetId() {
		return meetingItemSetId;
	}

	public void setMeetingItemSetId(int meetingItemSetId) {
		this.meetingItemSetId = meetingItemSetId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public Class getResponseClass() {
		return SetMeetingItemsStatusResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.SET_MEETING_ITEMS_STATUS;
	}
}