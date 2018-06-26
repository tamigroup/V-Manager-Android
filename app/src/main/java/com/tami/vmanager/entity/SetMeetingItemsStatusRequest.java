package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 设置单个会议流程节点的状态.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SetMeetingItemsStatusRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String mark;
	private String meetingItemSetId;
	private String status;
	private String userId;

	public SetMeetingItemsStatusRequest() {
		super();
	}	

	/**
	 * @return 节点有问题时的备注
	 */
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	/**
	 * @return 流程节点ID
	 */
	public String getMeetingItemSetId() {
		return meetingItemSetId;
	}

	public void setMeetingItemSetId(String meetingItemSetId) {
		this.meetingItemSetId = meetingItemSetId;
	}

	/**
	 * @return 设置的状态值  1代表通过  2代表有问题 3代表正常状态（灰色）
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 修改节点确认人的用户ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}