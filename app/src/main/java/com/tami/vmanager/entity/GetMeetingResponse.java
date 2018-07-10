package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询会议概览信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;

	public Item data;

	public GetMeetingResponse() {
		super();
	}

	@JsonInclude(Include.NON_NULL)
	public static class Item implements Serializable{
		private static final long serialVersionUID = 4172542378039500196L;
		public	int	saleUserId;	/*1*/
		public	String	saleUserName;	/*小塔米*/
		public	String	meetingAddress;	/*醉仙翁*/
		public	int	actualNum;	/*0*/
		public	String	eoUrl;	/**/
		public	String	isPay;	/**/
		public	String	perfectStatus;	/*待完善*/
		public	int	estimateNum;	/*100*/
		public	int	meetingAddressId;	/*3*/
		public	Long	endTime;	/*1533204602000*/
		public	int	minNum;	/*95*/
		public	int	fromPlat;	/*2*/
		public	int	isImportant;	/*0*/
		public	int	followStatus;	/*0*/
		public	Long	startTime;	/*1533118195000*/
		public	String	meetingName;	/*塔米G01加拿大会议*/
		public	String	sponsorName;	/**/
		public	String	meetingStatus;	/*待执行*/
		public	int	meetingId;	/*4*/
		public	String	autoDayTime;	/*8月1日18:09~8月2日18:10*/
		public	int	vzhStatus;	/*0*/
		public	String	salesUserMobile;	/*13800138000*/
	}
}