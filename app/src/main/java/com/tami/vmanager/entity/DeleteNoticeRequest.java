package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 删除公告.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeleteNoticeRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId ;
	private String noticeId;
	private String systemId;

	public DeleteNoticeRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getMeetingId () {
		return meetingId ;
	}

	public void setMeetingId (String meetingId ) {
		this.meetingId  = meetingId ;
	}

	/**
	 * @return 
	 */
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * @return 
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


}