package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 回复会议变化需求.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class ReplyMeetingRequirementRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meeetingRequirementId;
	private String replyContent;
	private String replyUserId;

	public ReplyMeetingRequirementRequest() {
		super();
	}	

	/**
	 * @return 需求变化ID
	 */
	public String getMeeetingRequirementId() {
		return meeetingRequirementId;
	}

	public void setMeeetingRequirementId(String meeetingRequirementId) {
		this.meeetingRequirementId = meeetingRequirementId;
	}

	/**
	 * @return 回复内容
	 */
	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	/**
	 * @return 回复人ID
	 */
	public String getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}


}