package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 查询会议概览信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;

	public GetMeetingRequest() {
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

	@Override
	public Class getResponseClass() {
		return GetMeetingResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.GET_MEETING;
	}
}