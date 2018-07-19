package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 会议需求变化.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateMeetingRequirementRequest implements Serializable{


	private static final long serialVersionUID = -8777357015853017933L;
	private String meetingId;
	private String requestContent;
	private String requestIconUrl;
	private String requestUserId;
	private String requestUserName;

	public CreateMeetingRequirementRequest() {
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
	 * @return 内容
	 */
	public String getRequestContent() {
		return requestContent;
	}

	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}

	/**
	 * @return 发布人头像
	 */
	public String getRequestIconUrl() {
		return requestIconUrl;
	}

	public void setRequestIconUrl(String requestIconUrl) {
		this.requestIconUrl = requestIconUrl;
	}

	/**
	 * @return 需求方人员ID
	 */
	public String getRequestUserId() {
		return requestUserId;
	}

	public void setRequestUserId(String requestUserId) {
		this.requestUserId = requestUserId;
	}

	/**
	 * @return 发布人姓名
	 */
	public String getRequestUserName() {
		return requestUserName;
	}

	public void setRequestUserName(String requestUserName) {
		this.requestUserName = requestUserName;
	}


}