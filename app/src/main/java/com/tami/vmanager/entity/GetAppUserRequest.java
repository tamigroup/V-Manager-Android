package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取用户信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetAppUserRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String userId;

	public GetAppUserRequest() {
		super();
	}	

	/**
	 * @return 获取用户信息
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}