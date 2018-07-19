package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 取消会议.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class DeleteUserMeetingResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -3680257788606611864L;

	public DeleteUserMeetingResponse() {
		super();
	}

	
	public boolean data;
}