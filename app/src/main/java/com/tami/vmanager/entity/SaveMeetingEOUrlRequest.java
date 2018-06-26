package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 保存会议EO单.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SaveMeetingEOUrlRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String eoUrl;
	private String meetingId;

	public SaveMeetingEOUrlRequest() {
		super();
	}	

	/**
	 * @return eo单Url地址
	 */
	public String getEoUrl() {
		return eoUrl;
	}

	public void setEoUrl(String eoUrl) {
		this.eoUrl = eoUrl;
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