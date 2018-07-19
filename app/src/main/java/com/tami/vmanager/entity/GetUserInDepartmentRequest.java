package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取部门用户组.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserInDepartmentRequest extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 6200187350846603706L;
	private int systemId;

	public GetUserInDepartmentRequest() {
		super();
	}

	public int getSystemId() {
		return systemId;
	}

	public void setSystemId(int systemId) {
		this.systemId = systemId;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.GET_USER_IN_DEPARTMENT;
	}

	@Override
	public Class getResponseClass() {
		return GetUserInDepartmentResponse.class;
	}
}