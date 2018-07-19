package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 回复会议变化需求.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class ReplyMeetingRequirementResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 2440845684280937047L;

	public ReplyMeetingRequirementResponse() {
		super();
	}

	

}