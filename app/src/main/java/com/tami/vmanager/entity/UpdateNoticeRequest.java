package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 编辑公告.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class UpdateNoticeRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String content;
	private String isTop;
	private String meetingId;
	private String noticeId;
	private String title;
	private String userId;

	public UpdateNoticeRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 
	 */
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	/**
	 * @return 
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return 
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}