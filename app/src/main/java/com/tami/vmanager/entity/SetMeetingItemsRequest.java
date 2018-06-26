package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 添加（ 编辑）会议流程节点.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SetMeetingItemsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String meetingItemsJson;
	private String startOn;

	public SetMeetingItemsRequest() {
		super();
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
	 * @return 流程节点ID，开始时间集合 json字符串  格式参考说明
	 */
	public String getMeetingItemsJson() {
		return meetingItemsJson;
	}

	public void setMeetingItemsJson(String meetingItemsJson) {
		this.meetingItemsJson = meetingItemsJson;
	}

	/**
	 * @return 会议举办时间 年月日
	 */
	public String getStartOn() {
		return startOn;
	}

	public void setStartOn(String startOn) {
		this.startOn = startOn;
	}


}