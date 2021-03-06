package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;


/**
 * 用户登录.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest  extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 7391852628438732189L;
	private String mobile;
	private String passWord;
	private String smsCode;
//	private String flag;

	public LoginRequest() {
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
	 * @return 密码
	 */
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public Class getResponseClass() {
		return LoginResponse.class;
	}


//	public String getFlag() {
//		return flag;
//	}
//
//	public void setFlag(String flag) {
//		this.flag = flag;
//	}

	@Override

	public String getRequestUrl() {
		return requestUrl;
	}

	private String requestUrl;

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
}