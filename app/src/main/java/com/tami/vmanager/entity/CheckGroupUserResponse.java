package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 校验用户是否在群中.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CheckGroupUserResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 2438118811266109472L;

	public CheckGroupUserResponse() {
		super();
	}

	

}