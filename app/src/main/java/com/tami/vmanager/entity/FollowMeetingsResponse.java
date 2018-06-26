package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询我关注的会议列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class FollowMeetingsResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.FollowMeetingsResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements {

		private String actualNum;
		private String endTime;
		private String estimateNum;
		private String isPay;
		private String meetingAddress;
		private String meetingId;
		private String meetingName;
		private String meetingStatus;
		private String minNum;
		private String saleUserId;
		private String saleUserName;
		private String startTime;

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
		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
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

	private List<ElementElements> elements;

	public FollowMeetingsResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public List<ElementElements> getElements() {
		return elements;
	}

	public void setElements(List<ElementElements> elements) {
		this.elements = elements;
	}
}