package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 判断用户是否有添加会议节点用户的权限.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CheckAddMeetingItemUserResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public CheckAddMeetingItemUserResponse() {
		super();
	}

	

}