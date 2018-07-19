package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 保存消息设置.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class SetUserNoticeConfigResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 474565370745721634L;

	public SetUserNoticeConfigResponse() {
		super();
	}

	

}