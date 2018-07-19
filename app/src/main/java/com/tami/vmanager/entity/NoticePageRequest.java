package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 分页查询公告列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class NoticePageRequest implements Serializable{


	private static final long serialVersionUID = 2623200481138124465L;
	private String curPage;
	private String groupType;
	private String meetingId;
	private String noticeType;
	private String pageSize;
	private String systemId;

	public NoticePageRequest() {
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
	 * @return 
	 */
	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
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
	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
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
	 * @return 
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


}