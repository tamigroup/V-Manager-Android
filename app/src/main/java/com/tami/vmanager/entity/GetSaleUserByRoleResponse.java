package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 根据酒店ID查销售人员列表（销售总+销售人员.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetSaleUserByRoleResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = -9013029348935281776L;

	/**
	 * @see com.bwapi.TM.message.entity.GetSaleUserByRoleResponse#getDataList
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementDataList implements Serializable{

		private static final long serialVersionUID = -1233716187824749942L;
		private String iconUrl;
		private String nickName;
		private String positionName;
		private String realName;

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
		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		/**
		 * @return 
		 */
		public String getPositionName() {
			return positionName;
		}

		public void setPositionName(String positionName) {
			this.positionName = positionName;
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
	}

	private List<ElementDataList> dataList;

	public GetSaleUserByRoleResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public List<ElementDataList> getDataList() {
		return dataList;
	}

	public void setDataList(List<ElementDataList> dataList) {
		this.dataList = dataList;
	}
}