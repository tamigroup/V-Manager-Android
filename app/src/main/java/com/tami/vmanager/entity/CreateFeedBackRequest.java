package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * V智会VV群发送反馈信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateFeedBackRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String content;
	private String fromType;
	private String iconUrl;
	private String meetingId;
	private String toType;
	private String userId;
	private String userName;

	public CreateFeedBackRequest() {
		super();
	}	

	/**
	 * @return 反馈内容
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 来源
	 */
	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	/**
	 * @return 
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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
	 * @return 
	 */
	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
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

	/**
	 * @return 
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}