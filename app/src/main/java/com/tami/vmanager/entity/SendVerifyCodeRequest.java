package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 请求发送验证码.客户端请求
 *
 * @author 代码生成器v1.0
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendVerifyCodeRequest extends MobileMessage implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private String mobile;

	public SendVerifyCodeRequest() {
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

	@Override
	public Class getResponseClass() {
		return SendVerifyCodeResponse.class;
	}

	@Override
	public String getRequsetUrl() {
		return HttpKey.USER_SEND_VERIFY_CODE;
	}
}