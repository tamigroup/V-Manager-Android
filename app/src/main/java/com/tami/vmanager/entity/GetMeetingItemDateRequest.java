package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 编辑节点弹窗数据接口.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingItemDateRequest implements Serializable{


	private static final long serialVersionUID = -4257839878127155057L;
	private String meetingItemSetId;

	public GetMeetingItemDateRequest() {
		super();
	}	

	/**
	 * @return 会议节点ID
	 */
	public String getMeetingItemSetId() {
		return meetingItemSetId;
	}

	public void setMeetingItemSetId(String meetingItemSetId) {
		this.meetingItemSetId = meetingItemSetId;
	}


}