package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 保存添加参会人数.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SaveGuestNumRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String estimateNum;
	private String meetingId;
	private String minNum;

	public SaveGuestNumRequest() {
		super();
	}	

	/**
	 * @return 预计人数
	 */
	public String getEstimateNum() {
		return estimateNum;
	}

	public void setEstimateNum(String estimateNum) {
		this.estimateNum = estimateNum;
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
	 * @return 保底人数
	 */
	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}


}