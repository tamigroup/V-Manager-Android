package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 获取所有职位及职位下的人员列表.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class UserListOfPositionRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String systemId;

	public UserListOfPositionRequest() {
		super();
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