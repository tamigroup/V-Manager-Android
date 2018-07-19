package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 编辑保存是否重要会议.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SaveIsImportantRequest implements Serializable{


	private static final long serialVersionUID = 4865164312446893052L;
	private String isImportant;
	private String meetingId;

	public SaveIsImportantRequest() {
		super();
	}	

	/**
	 * @return 是否重要 1重要 2 不重要
	 */
	public String getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
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


}