package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 查询VIP成员列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class VipGusetPageRequest implements Serializable{


	private static final long serialVersionUID = 3852543818074562548L;
	private String curPage;
	private String meetingId;
	private String pageSize;
	private String systemId;

	public VipGusetPageRequest() {
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
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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


}