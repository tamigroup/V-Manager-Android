package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 手机号和验证码登陆.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
@Deprecated
public class LoginSmsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String mobile;
	private String smsCode;

	public LoginSmsRequest() {
		super();
	}	

	/**
	 * @return 手机号
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 短信验证码
	 */
	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}


}