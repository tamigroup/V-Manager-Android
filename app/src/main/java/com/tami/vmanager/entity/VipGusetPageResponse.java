package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询VIP成员列表.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class VipGusetPageResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.VipGusetPageResponse#getElements
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementElements {

		private String createOn;
		private String id;
		private String intro;
		private String meetingId;
		private String name;
		private String status;
		private String systemId;
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
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return 
		 */
		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
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
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
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
	}

	private String curPage;
	private String firstPage;
	private String lastPage;
	private String lastPageNumber;
	private String nextPage;
	private String pageSize;
	private String thisPageLastElementNumber;
	private String totalElements;
	private List<ElementElements> elements;

	public VipGusetPageResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public String getCurPage() {
		return curPage;
	}

	public void setCurPage(String curPage) {
		this.curPage = curPage;
	}

	/**
	 * @return 
	 */
	public String getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(String firstPage) {
		this.firstPage = firstPage;
	}

	/**
	 * @return 
	 */
	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * @return 
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}

	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}

	/**
	 * @return 
	 */
	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	/**
	 * @return 
	 */
	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return 
	 */
	public String getThisPageLastElementNumber() {
		return thisPageLastElementNumber;
	}

	public void setThisPageLastElementNumber(String thisPageLastElementNumber) {
		this.thisPageLastElementNumber = thisPageLastElementNumber;
	}

	/**
	 * @return 
	 */
	public String getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
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