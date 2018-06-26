package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取会议群组中的用户成员.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class MeetingUserGroupPageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String curPage;
	private String meetingId;
	private String pageSize;

	public MeetingUserGroupPageRequest() {
		super();
	}	

	/**
	 * @return 页码
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


}