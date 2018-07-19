package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 从会议分组中删除用户(退群).服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class OutGroupUserResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -5190534397362155344L;

	public OutGroupUserResponse() {
		super();
	}

	

}