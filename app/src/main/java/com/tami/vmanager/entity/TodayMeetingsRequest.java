package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 查询今日会议列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class TodayMeetingsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String curPage;
	private String pageSize;
	private String userId;

	public TodayMeetingsRequest() {
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
	 * @return size
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
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