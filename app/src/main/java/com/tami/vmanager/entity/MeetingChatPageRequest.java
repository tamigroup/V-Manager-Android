package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 群消息列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class MeetingChatPageRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private int curPage;
	private int meetingId;
	private int pageSize;
	private int type;

	public MeetingChatPageRequest() {
		super();
	}	

	/**
	 * @return 当前页
	 */
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	/**
	 * @return 会议ID
	 */
	public int getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 页大小
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 类别  1-v管家  2-v智会 3-VV群
	 */
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public Class getResponseClass() {
		return MeetingChatPageResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.GET_MEETING_GROUP_CHAT;
	}
}