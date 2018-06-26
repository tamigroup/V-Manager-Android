package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 创建公告.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateNoticeRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String content;
	private String groupType;
	private String isTop;
	private String meetingId;
	private String noticeType;
	private String systemId;
	private String title;
	private String userId;

	public CreateNoticeRequest() {
		super();
	}	

	/**
	 * @return 公告内容
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return 群组类型  1-V管家 2-V智会 3-VV群
	 */
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return 是否置顶
	 */
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
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
	 * @return 会议类型   1-系统通知  2-会务广播 3-群内公告
	 */
	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	/**
	 * @return 酒店ID
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return 公告标题
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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


}