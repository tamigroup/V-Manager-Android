package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 查询系统内置的会议流程所有节点.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingItemsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String startDate;

	public GetMeetingItemsRequest() {
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
	 * @return 会议举办时间
	 */
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


}