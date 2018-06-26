package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 编辑保存VIP接待人员.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SaveReceiveUserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String meetingId;
	private String vipReseiveUserId;

	public SaveReceiveUserRequest() {
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
	 * @return 接待人员ID
	 */
	public String getVipReseiveUserId() {
		return vipReseiveUserId;
	}

	public void setVipReseiveUserId(String vipReseiveUserId) {
		this.vipReseiveUserId = vipReseiveUserId;
	}


}