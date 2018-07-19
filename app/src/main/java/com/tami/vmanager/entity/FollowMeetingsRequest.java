package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 查询我关注的会议列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class FollowMeetingsRequest implements Serializable{


	private static final long serialVersionUID = 29485181818602687L;
	private String curPage;
	private String endDate;
	private String pageSize;
	private String searchType;
	private String startDate;
	private String userId;

	public FollowMeetingsRequest() {
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
	 * @return 如果searchType=1  此参数可不传
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return 每页条数
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 查询类型【searchType=1，不考虑时间段、searchType=0，时间段查询，此时查询条件需要加上starDate,endDate】， 此字段必传
	 */
	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return 如果searchType=1  此参数可不传
	 */
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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