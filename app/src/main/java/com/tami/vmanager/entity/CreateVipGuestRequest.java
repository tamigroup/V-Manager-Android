package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 添加VIP成员信息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class CreateVipGuestRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String intro;
	private String meetingId;
	private String name;
	private String systemId;

	public CreateVipGuestRequest() {
		super();
	}	

	/**
	 * @return VIP介绍
	 */
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return 会议ID
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return VIP 姓名
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 酒店ID
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public Class getResponseClass() {
		return CreateVipGuestResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.CREATE_VIPGUEST;
	}
}