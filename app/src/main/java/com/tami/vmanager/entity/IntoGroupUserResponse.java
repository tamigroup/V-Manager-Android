package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 添加用户到会议分组.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class IntoGroupUserResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 4048217003732416250L;

	public IntoGroupUserResponse() {
		super();
	}

	

}