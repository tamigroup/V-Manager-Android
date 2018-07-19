package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 注销登陆.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class LoginOutResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -3614941258225194625L;

	public LoginOutResponse() {
		super();
	}

	

}