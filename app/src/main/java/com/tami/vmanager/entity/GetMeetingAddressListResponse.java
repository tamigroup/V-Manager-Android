package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 根据酒店ID查询会议厅List.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingAddressListResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.GetMeetingAddressListResponse#getDataList
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementDataList {

		private String area;
		private String id;
		private String name;

		/**
		 * @return 
		 */
		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
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
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	private List<ElementDataList> dataList;

	public GetMeetingAddressListResponse() {
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