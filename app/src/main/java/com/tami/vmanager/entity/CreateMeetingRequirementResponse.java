package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 会议需求变化.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class CreateMeetingRequirementResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 7496450074244829352L;

	public CreateMeetingRequirementResponse() {
		super();
	}

	

}