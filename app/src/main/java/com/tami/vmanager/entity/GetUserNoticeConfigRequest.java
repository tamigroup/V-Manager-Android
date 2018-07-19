package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取消息设置.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetUserNoticeConfigRequest extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 6197044171411450598L;
	private String userId;

	public GetUserNoticeConfigRequest() {
		super();
	}	

	/**
	 * @return 获取消息设置
	 */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public Class getResponseClass() {
		return GetUserNoticeConfigResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.USER_GET_NOTICE_CONFIG;
	}
}