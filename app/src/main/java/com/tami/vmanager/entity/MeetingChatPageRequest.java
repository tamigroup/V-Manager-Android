package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 群消息列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class MeetingChatPageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String curPage;
	private String meetingId;
	private String pageSize;
	private String type;

	public MeetingChatPageRequest() {
		super();
	}	

	/**
	 * @return 当前页
	 */
	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
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
	 * @return 页大小
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 类别  1-v管家  2-v智会 3-VV群
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}