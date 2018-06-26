package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 评价列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class EvaluatePageRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String curPage;
	private String meetingId;
	private String pageSize;
	private String type;

	public EvaluatePageRequest() {
		super();
	}	

	/**
	 * @return 1
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
	 * @return 10
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 2-举办方  3-参会方   0-取全部
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}