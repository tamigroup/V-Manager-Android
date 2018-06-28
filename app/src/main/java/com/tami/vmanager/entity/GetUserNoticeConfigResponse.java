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


	public GetUserNoticeConfigResponse() {
		super();
	}

	public Item data;

	public static class Item{
		private String createOn;
		private int groupchatNotice;
		private int hostNotice;
		private int meetingNotice;
		private int satisfactionNotice;
		private int systemNotice;
		private String updateOn;

		public String getCreateOn() {
			return createOn;
		}

		public void setCreateOn(String createOn) {
			this.createOn = createOn;
		}

		public int getGroupchatNotice() {
			return groupchatNotice;
		}

		public void setGroupchatNotice(int groupchatNotice) {
			this.groupchatNotice = groupchatNotice;
		}

		public int getHostNotice() {
			return hostNotice;
		}

		public void setHostNotice(int hostNotice) {
			this.hostNotice = hostNotice;
		}

		public int getMeetingNotice() {
			return meetingNotice;
		}

		public void setMeetingNotice(int meetingNotice) {
			this.meetingNotice = meetingNotice;
		}

		public int getSatisfactionNotice() {
			return satisfactionNotice;
		}

		public void setSatisfactionNotice(int satisfactionNotice) {
			this.satisfactionNotice = satisfactionNotice;
		}

		public int getSystemNotice() {
			return systemNotice;
		}

		public void setSystemNotice(int systemNotice) {
			this.systemNotice = systemNotice;
		}

		public String getUpdateOn() {
			return updateOn;
		}

		public void setUpdateOn(String updateOn) {
			this.updateOn = updateOn;
		}
	}
}