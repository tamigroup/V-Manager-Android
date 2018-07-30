package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;


/**
 * 校验用户是否在群中.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CheckGroupUserResponseBean extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 2438118811266109472L;
	/**
	 * data : true
	 */

	private boolean data;

	public CheckGroupUserResponseBean() {
		super();
	}


	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}
}