package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 添加评价.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateEvaluateRequest implements Serializable{


	private static final long serialVersionUID = -8639903290137247660L;
	private String content;
	private String iconUrl;
	private String meetingId;
	private String score;
	private String type;
	private String userId;
	private String userName;

	public CreateEvaluateRequest() {
		super();
	}	

	/**
	 * @return 评价内容 
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 评论人头像
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
	 * @return 分数  5
	 */
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return 类别  2-举办方  3-参会方
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 用户ID
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return 评论人真实姓名 
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}