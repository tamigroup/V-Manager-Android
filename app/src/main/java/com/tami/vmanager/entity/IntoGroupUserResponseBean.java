package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 添加用户到会议分组.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class IntoGroupUserResponseBean extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 4048217003732416250L;
	/**
	 * data : true
	 */

	private boolean data;

	public IntoGroupUserResponseBean() {
		super();
	}


	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.INTOGROUPUSER;
	}

	@Override
	public Class getResponseClass() {
		return IntoGroupUserResponseBean.class;
	}
}