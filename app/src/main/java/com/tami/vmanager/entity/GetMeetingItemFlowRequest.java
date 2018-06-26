package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取会议流程单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingItemFlowRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;

	public GetMeetingItemFlowRequest() {
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


}