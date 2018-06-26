package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取所有职位及职位下的人员列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class UserListOfPositionResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.UserListOfPositionResponse#getDataList
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementDataList {

		private String createOn;
		private String departmentName;
		private String depId;
		private String id;
		private String orderBy;
		private String positionName;
		private String systemId;
		private String updateOn;
		private String userCounts;

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
		public String getDepartmentName() {
			return departmentName;
		}

		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}

		/**
		 * @return 
		 */
		public String getDepId() {
			return depId;
		}

		public void setDepId(String depId) {
			this.depId = depId;
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
		public String getOrderBy() {
			return orderBy;
		}

		public void setOrderBy(String orderBy) {
			this.orderBy = orderBy;
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
		public String getSystemId() {
			return systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
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
		public String getUserCounts() {
			return userCounts;
		}

		public void setUserCounts(String userCounts) {
			this.userCounts = userCounts;
		}
	}

	private List<ElementDataList> dataList;

	public UserListOfPositionResponse() {
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