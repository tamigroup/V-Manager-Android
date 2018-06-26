package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 群消息列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MeetingChatPageResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.MeetingChatPageResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements {

		private String content;
		private String createOn;
		private String id;
		private String sendDate;
		private String status;
		private String updateOn;
		private String userId;
		private String userName;

		/**
		 * @return 
		 */
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
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
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 
		 */
		public String getSendDate() {
			return sendDate;
		}

		public void setSendDate(String sendDate) {
			this.sendDate = sendDate;
		}

		/**
		 * @return 
		 */
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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

		/**
		 * @return 
		 */
		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * @return 
		 */
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	private List<ElementElements> elements;

	public MeetingChatPageResponse() {
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