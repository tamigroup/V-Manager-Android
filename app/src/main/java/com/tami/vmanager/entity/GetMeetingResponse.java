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
	private static final long serialVersionUID = 1L;


	private String actualNum;
	private String autoDayTime;
	private String endTime;
	private String eoUrl;
	private String estimateNum;
	private String fromPlat;
	private String isFollow;
	private String isImportant;
	private String isPay;
	private String meetingAddress;
	private String meetingAddressId;
	private String meetingId;
	private String meetingName;
	private String meetingStatus;
	private String minNum;
	private String saleUserId;
	private String saleUserName;
	private String startTime;

	public GetMeetingResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public String getActualNum() {
		return actualNum;
	}

	public void setActualNum(String actualNum) {
		this.actualNum = actualNum;
	}

	/**
	 * @return 
	 */
	public String getAutoDayTime() {
		return autoDayTime;
	}

	public void setAutoDayTime(String autoDayTime) {
		this.autoDayTime = autoDayTime;
	}

	/**
	 * @return 
	 */
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return 
	 */
	public String getEoUrl() {
		return eoUrl;
	}

	public void setEoUrl(String eoUrl) {
		this.eoUrl = eoUrl;
	}

	/**
	 * @return 
	 */
	public String getEstimateNum() {
		return estimateNum;
	}

	public void setEstimateNum(String estimateNum) {
		this.estimateNum = estimateNum;
	}

	/**
	 * @return 
	 */
	public String getFromPlat() {
		return fromPlat;
	}

	public void setFromPlat(String fromPlat) {
		this.fromPlat = fromPlat;
	}

	/**
	 * @return 
	 */
	public String getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(String isFollow) {
		this.isFollow = isFollow;
	}

	/**
	 * @return 
	 */
	public String getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}

	/**
	 * @return 
	 */
	public String getIsPay() {
		return isPay;
	}

	public void setIsPay(String isPay) {
		this.isPay = isPay;
	}

	/**
	 * @return 
	 */
	public String getMeetingAddress() {
		return meetingAddress;
	}

	public void setMeetingAddress(String meetingAddress) {
		this.meetingAddress = meetingAddress;
	}

	/**
	 * @return 
	 */
	public String getMeetingAddressId() {
		return meetingAddressId;
	}

	public void setMeetingAddressId(String meetingAddressId) {
		this.meetingAddressId = meetingAddressId;
	}

	/**
	 * @return 
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 
	 */
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	/**
	 * @return 
	 */
	public String getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(String meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	/**
	 * @return 
	 */
	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}

	/**
	 * @return 
	 */
	public String getSaleUserId() {
		return saleUserId;
	}

	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	/**
	 * @return 
	 */
	public String getSaleUserName() {
		return saleUserName;
	}

	public void setSaleUserName(String saleUserName) {
		this.saleUserName = saleUserName;
	}

	/**
	 * @return 
	 */
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}