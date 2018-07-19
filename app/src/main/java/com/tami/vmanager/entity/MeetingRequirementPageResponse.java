package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取需求变化分页数据.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MeetingRequirementPageResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 166178998350728708L;

	/**
	 * @see com.bwapi.TM.message.entity.MeetingRequirementPageResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements implements Serializable{

		private static final long serialVersionUID = 6035169946447937827L;
		private String createOn;
		private String meetingId;
		private String replyContent;
		private String replyStatus;
		private String replyUserId;
		private String replyUserName;
		private String requestContent;
		private String requestIconUrl;
		private String requestOn;
		private String requestTime;
		private String requestUserId;
		private String requestUserName;
		private String updateOn;

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
		public String getMeetingId() {
			return meetingId;
		}

		public void setMeetingId(String meetingId) {
			this.meetingId = meetingId;
		}

		/**
		 * @return 
		 */
		public String getReplyContent() {
			return replyContent;
		}

		public void setReplyContent(String replyContent) {
			this.replyContent = replyContent;
		}

		/**
		 * @return 
		 */
		public String getReplyStatus() {
			return replyStatus;
		}

		public void setReplyStatus(String replyStatus) {
			this.replyStatus = replyStatus;
		}

		/**
		 * @return 
		 */
		public String getReplyUserId() {
			return replyUserId;
		}

		public void setReplyUserId(String replyUserId) {
			this.replyUserId = replyUserId;
		}

		/**
		 * @return 
		 */
		public String getReplyUserName() {
			return replyUserName;
		}

		public void setReplyUserName(String replyUserName) {
			this.replyUserName = replyUserName;
		}

		/**
		 * @return 
		 */
		public String getRequestContent() {
			return requestContent;
		}

		public void setRequestContent(String requestContent) {
			this.requestContent = requestContent;
		}

		/**
		 * @return 
		 */
		public String getRequestIconUrl() {
			return requestIconUrl;
		}

		public void setRequestIconUrl(String requestIconUrl) {
			this.requestIconUrl = requestIconUrl;
		}

		/**
		 * @return 
		 */
		public String getRequestOn() {
			return requestOn;
		}

		public void setRequestOn(String requestOn) {
			this.requestOn = requestOn;
		}

		/**
		 * @return 
		 */
		public String getRequestTime() {
			return requestTime;
		}

		public void setRequestTime(String requestTime) {
			this.requestTime = requestTime;
		}

		/**
		 * @return 
		 */
		public String getRequestUserId() {
			return requestUserId;
		}

		public void setRequestUserId(String requestUserId) {
			this.requestUserId = requestUserId;
		}

		/**
		 * @return 
		 */
		public String getRequestUserName() {
			return requestUserName;
		}

		public void setRequestUserName(String requestUserName) {
			this.requestUserName = requestUserName;
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

	private List<ElementElements> elements;

	public MeetingRequirementPageResponse() {
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