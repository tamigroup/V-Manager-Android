package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;


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
	public static final long serialVersionUID = 1L;


	public GetUserNoticeConfigResponse() {
		super();
	}

	public Item data;

	public static class Item{
		public	int	meetingNotice;	/*0 -会务广播*/
		public	int	id;	/*2*/
		public	int	groupchatNotice;	/*1 -满意度通知*/
		public	int	satisfactionNotice;	/*1 -群聊通知*/
		public	int	systemNotice;	/*0 -系统消息*/
		public	int	hostNotice;	/*1 -主办方消息*/
		public	int	userId;	/*33*/
		public	Long	createOn;	/*1530755883000*/
		public	int	taskDistribution;	/*0*/
		public	Long	updateOn;	/*1530755884000*/
	}
}