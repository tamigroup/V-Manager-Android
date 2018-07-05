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
	public static final long serialVersionUID = 1L;


	public GetUserNoticeConfigResponse() {
		super();
	}

	public Item data;

	public static class Item{
		public	int	id;	/*1*/
		public	int	meetingNotice;	/*1*/
		public	int	groupchatNotice;	/*0*/
		public	int	satisfactionNotice;	/*0*/
		public	int	systemNotice;	/*1*/
		public	int	hostNotice;	/*1*/
		public	int	userId;	/*1*/
		public	Long	createOn;	/*1528767815000*/
		public	Long	updateOn;	/*1528768343000*/
	}
}