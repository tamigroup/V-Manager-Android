package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 更改密码.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class ChangePasswordResponse extends MobileMessage implements Serializable{

	private static final long serialVersionUID = -2280948128764190468L;

	public ChangePasswordResponse() {
		super();
	}

	public boolean data;

}