package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 创建会议.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateMeetingRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String createMeetingUserId;
	private String endDate;
	private String meetingAddressId;
	private String meetingName;
	private String startDate;
	private String systemId;

	public CreateMeetingRequest() {
		super();
	}	

	/**
	 * @return 销售人员ID
	 */
	public String getCreateMeetingUserId() {
		return createMeetingUserId;
	}

	public void setCreateMeetingUserId(String createMeetingUserId) {
		this.createMeetingUserId = createMeetingUserId;
	}

	/**
	 * @return 结束时间
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return 会议厅ID
	 */
	public String getMeetingAddressId() {
		return meetingAddressId;
	}

	public void setMeetingAddressId(String meetingAddressId) {
		this.meetingAddressId = meetingAddressId;
	}

	/**
	 * @return 酒店名称
	 */
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	/**
	 * @return 开始时间
	 */
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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