package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取公告.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetNoticeResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -5289597449513090401L;

	/**
	 * @see com.bwapi.TM.message.entity.GetNoticeResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements implements  Serializable{

		private static final long serialVersionUID = 511030957770378379L;
		private String content;
		private String createOn;
		private String iconUrl;
		private String isTop;
		private String mobile;
		private String realName;
		private String updateOn;
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
		public String getIconUrl() {
			return iconUrl;
		}

		public void setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
		}

		/**
		 * @return 
		 */
		public String getIsTop() {
			return isTop;
		}

		public void setIsTop(String isTop) {
			this.isTop = isTop;
		}

		/**
		 * @return 
		 */
		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		/**
		 * @return 
		 */
		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
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
		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	private List<ElementElements> elements;

	public GetNoticeResponse() {
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