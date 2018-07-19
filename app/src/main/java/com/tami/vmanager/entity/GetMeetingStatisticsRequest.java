package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 查询会议统计信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingStatisticsRequest implements Serializable{


	private static final long serialVersionUID = 7519114296666110193L;
	private String meetingId;

	public GetMeetingStatisticsRequest() {
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