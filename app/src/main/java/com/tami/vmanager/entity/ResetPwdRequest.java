package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 重置密码.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */

public class ResetPwdRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String newPassWord;
	private String smsCode;
	private String mobile;

	public ResetPwdRequest() {
		super();
	}	

	/**
	 * @return 新密码
	 */
	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	/**
	 * @return SMS Code
	 */
	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.USER_RESET_PASSOWRD;
	}

	@Override
	public Class getResponseClass() {
		return ResetPwdResponse.class;
	}
}