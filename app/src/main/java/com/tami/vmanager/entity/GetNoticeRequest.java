package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取公告.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetNoticeRequest implements Serializable{


	private static final long serialVersionUID = 7552610195368386574L;
	private String noticeId;

	public GetNoticeRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}


}