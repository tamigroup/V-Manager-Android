package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取会议群组中的用户成员.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MeetingUserGroupPageResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.MeetingUserGroupPageResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements {

		private String iconUrl;
		private String mobile;
		private String realName;
		private String status;

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
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}

	private List<ElementElements> elements;

	public MeetingUserGroupPageResponse() {
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