package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 绑定用户RegistrationId.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SetUserRegistrationIdRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String registrationId;
	private String userId;

	public SetUserRegistrationIdRequest() {
		super();
	}	

	/**
	 * @return 回调极光函数得到的RegistrationId
	 */
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	/**
	 * @return 经度
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


}