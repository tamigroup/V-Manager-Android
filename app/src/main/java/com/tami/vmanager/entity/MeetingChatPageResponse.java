package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;


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
	 * data : {"curPage":1,"elements":[{"content":"哈哈哈哈哈哈333！！！！","createOn":1529457351000,"id":9,"meetingId":1,"sendDate":"09:15","status":0,"type":1,"updateOn":1529457351000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈22！！！！","createOn":1529457283000,"id":8,"meetingId":1,"sendDate":"09:14","status":0,"type":1,"updateOn":1529457283000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈22！！！！","createOn":1529457191000,"id":7,"meetingId":1,"sendDate":"09:13","status":0,"type":1,"updateOn":1529457191000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈！！！！","createOn":1529456955000,"id":6,"meetingId":1,"sendDate":"09:09","status":0,"type":1,"updateOn":1529456955000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"明天好冷酷","createOn":1528714536000,"id":3,"meetingId":1,"sendDate":"18:55","status":0,"type":1,"updateOn":1528714536000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":12,"userName":"未知用户-wangdachui"},{"content":"今天好温暖","createOn":1528714529000,"id":2,"meetingId":1,"sendDate":"18:55","status":0,"type":1,"updateOn":1528714529000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":12,"userName":"未知用户-wangdachui"},{"content":"这是啥","createOn":1528714465000,"id":1,"meetingId":1,"sendDate":"18:54","status":0,"type":1,"updateOn":1528714465000,"userIcon":"http://f.tamiyun.com/app/af20180709172918_44812.png","userId":1,"userName":"研发部-小塔米"}],"firstPage":true,"lastPage":true,"lastPageNumber":1,"nextPage":2,"pageSize":10,"previousPage":0,"thisPageFirstElementNumber":1,"thisPageLastElementNumber":7,"totalElements":7}
	 */

	private DataBean data;

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}
	@JsonInclude(Include.NON_NULL)
	public static class DataBean {
		/**
		 * curPage : 1
		 * elements : [{"content":"哈哈哈哈哈哈333！！！！","createOn":1529457351000,"id":9,"meetingId":1,"sendDate":"09:15","status":0,"type":1,"updateOn":1529457351000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈22！！！！","createOn":1529457283000,"id":8,"meetingId":1,"sendDate":"09:14","status":0,"type":1,"updateOn":1529457283000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈22！！！！","createOn":1529457191000,"id":7,"meetingId":1,"sendDate":"09:13","status":0,"type":1,"updateOn":1529457191000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"哈哈哈哈哈哈！！！！","createOn":1529456955000,"id":6,"meetingId":1,"sendDate":"09:09","status":0,"type":1,"updateOn":1529456955000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":0,"userName":"未知用户-null"},{"content":"明天好冷酷","createOn":1528714536000,"id":3,"meetingId":1,"sendDate":"18:55","status":0,"type":1,"updateOn":1528714536000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":12,"userName":"未知用户-wangdachui"},{"content":"今天好温暖","createOn":1528714529000,"id":2,"meetingId":1,"sendDate":"18:55","status":0,"type":1,"updateOn":1528714529000,"userIcon":"http://www.tx2010.com/images/pic_list1.jpg","userId":12,"userName":"未知用户-wangdachui"},{"content":"这是啥","createOn":1528714465000,"id":1,"meetingId":1,"sendDate":"18:54","status":0,"type":1,"updateOn":1528714465000,"userIcon":"http://f.tamiyun.com/app/af20180709172918_44812.png","userId":1,"userName":"研发部-小塔米"}]
		 * firstPage : true
		 * lastPage : true
		 * lastPageNumber : 1
		 * nextPage : 2
		 * pageSize : 10
		 * previousPage : 0
		 * thisPageFirstElementNumber : 1
		 * thisPageLastElementNumber : 7
		 * totalElements : 7
		 */

		private int curPage;
		private boolean firstPage;
		private boolean lastPage;
		private int lastPageNumber;
		private int nextPage;
		private int pageSize;
		private int previousPage;
		private int thisPageFirstElementNumber;
		private int thisPageLastElementNumber;
		private int totalElements;
		private List<ElementsBean> elements;

		public int getCurPage() {
			return curPage;
		}

		public void setCurPage(int curPage) {
			this.curPage = curPage;
		}

		public boolean isFirstPage() {
			return firstPage;
		}

		public void setFirstPage(boolean firstPage) {
			this.firstPage = firstPage;
		}

		public boolean isLastPage() {
			return lastPage;
		}

		public void setLastPage(boolean lastPage) {
			this.lastPage = lastPage;
		}

		public int getLastPageNumber() {
			return lastPageNumber;
		}

		public void setLastPageNumber(int lastPageNumber) {
			this.lastPageNumber = lastPageNumber;
		}

		public int getNextPage() {
			return nextPage;
		}

		public void setNextPage(int nextPage) {
			this.nextPage = nextPage;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public int getPreviousPage() {
			return previousPage;
		}

		public void setPreviousPage(int previousPage) {
			this.previousPage = previousPage;
		}

		public int getThisPageFirstElementNumber() {
			return thisPageFirstElementNumber;
		}

		public void setThisPageFirstElementNumber(int thisPageFirstElementNumber) {
			this.thisPageFirstElementNumber = thisPageFirstElementNumber;
		}

		public int getThisPageLastElementNumber() {
			return thisPageLastElementNumber;
		}

		public void setThisPageLastElementNumber(int thisPageLastElementNumber) {
			this.thisPageLastElementNumber = thisPageLastElementNumber;
		}

		public int getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(int totalElements) {
			this.totalElements = totalElements;
		}

		public List<ElementsBean> getElements() {
			return elements;
		}

		public void setElements(List<ElementsBean> elements) {
			this.elements = elements;
		}
		@JsonInclude(Include.NON_NULL)
		public static class ElementsBean {
			/**
			 * content : 哈哈哈哈哈哈333！！！！
			 * createOn : 1529457351000
			 * id : 9
			 * meetingId : 1
			 * sendDate : 09:15
			 * status : 0
			 * type : 1
			 * updateOn : 1529457351000
			 * userIcon : http://www.tx2010.com/images/pic_list1.jpg
			 * userId : 0
			 * userName : 未知用户-null
			 */

			private String content;
			private long createOn;
			private int id;
			private String meetingId;
			private String sendDate;
			private int status;
			private String type;
			private long updateOn;
			private String userIcon;
			private int userId;
			private String userName;

			public String getContent() {
				return content;
			}

			public void setContent(String content) {
				this.content = content;
			}

			public long getCreateOn() {
				return createOn;
			}

			public void setCreateOn(long createOn) {
				this.createOn = createOn;
			}

			public int getId() {
				return id;
			}

			public void setId(int id) {
				this.id = id;
			}

			public String getMeetingId() {
				return meetingId;
			}

			public void setMeetingId(String meetingId) {
				this.meetingId = meetingId;
			}

			public String getSendDate() {
				return sendDate;
			}

			public void setSendDate(String sendDate) {
				this.sendDate = sendDate;
			}

			public int getStatus() {
				return status;
			}

			public void setStatus(int status) {
				this.status = status;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public long getUpdateOn() {
				return updateOn;
			}

			public void setUpdateOn(long updateOn) {
				this.updateOn = updateOn;
			}

			public String getUserIcon() {
				return userIcon;
			}

			public void setUserIcon(String userIcon) {
				this.userIcon = userIcon;
			}

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
			}

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}
		}
	}
}