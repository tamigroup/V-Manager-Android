package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取首页会议总计.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetIndexRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String userId;

	public GetIndexRequest() {
		super();
	}	

	/**
	 * @return 
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getRequsetUrl() {
		return HttpKey.USER_GET_INDEX;
	}

	@Override
	public Class getResponseClass() {
		return GetIndexResponse.class;
	}
}