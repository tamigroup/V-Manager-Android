package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 根据会议ID查询会议节点信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingItemsByMeetingIdRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;

	public GetMeetingItemsByMeetingIdRequest() {
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