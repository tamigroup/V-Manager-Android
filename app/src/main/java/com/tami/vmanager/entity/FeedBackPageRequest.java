package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取反馈信息列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class FeedBackPageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String curPage;
	private String meetingId;
	private String pageSize;
	private String toType;

	public FeedBackPageRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
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
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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


}