package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取消息设置.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetUserNoticeConfigResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String createOn;
	private String groupchatNotice;
	private String hostNotice;
	private String meetingNotice;
	private String satisfactionNotice;
	private String systemNotice;
	private String updateOn;

	public GetUserNoticeConfigResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	/**
	 * @return 
	 */
	public String getGroupchatNotice() {
		return groupchatNotice;
	}

	public void setGroupchatNotice(String groupchatNotice) {
		this.groupchatNotice = groupchatNotice;
	}

	/**
	 * @return 
	 */
	public String getHostNotice() {
		return hostNotice;
	}

	public void setHostNotice(String hostNotice) {
		this.hostNotice = hostNotice;
	}

	/**
	 * @return 
	 */
	public String getMeetingNotice() {
		return meetingNotice;
	}

	public void setMeetingNotice(String meetingNotice) {
		this.meetingNotice = meetingNotice;
	}

	/**
	 * @return 
	 */
	public String getSatisfactionNotice() {
		return satisfactionNotice;
	}

	public void setSatisfactionNotice(String satisfactionNotice) {
		this.satisfactionNotice = satisfactionNotice;
	}

	/**
	 * @return 
	 */
	public String getSystemNotice() {
		return systemNotice;
	}

	public void setSystemNotice(String systemNotice) {
		this.systemNotice = systemNotice;
	}

	/**
	 * @return 
	 */
	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
}