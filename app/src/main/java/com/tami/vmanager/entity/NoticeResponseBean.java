package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/5
 * 公告 服务器获取实体类
 */
@JsonInclude(Include.NON_NULL)
public class NoticeResponseBean extends MobileMessage implements Serializable {


    private static final long serialVersionUID = -2205623261780545784L;
    /**
     * data : {"curPage":1,"elements":[{"content":"本群须知20180613","createOn":1528857476000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":7,"isTop":1,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"测试公告20180613","updateOn":1528857476000,"userId":2,"userName":"李小二"},{"content":"此会议将于7月01日14：00准时召开，请做好准备！","createOn":1528697847000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":3,"isTop":1,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528697847000,"userId":1,"userName":"小塔米"},{"content":"本群须知","createOn":1528856114000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":6,"isTop":0,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528856114000,"userId":1,"userName":"小塔米"},{"content":"此会议将于6月15日10：00准时召开，请做好准备！","createOn":1528698140000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":4,"isTop":0,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528698140000,"userId":1,"userName":"小塔米"}],"firstPage":true,"lastPage":true,"lastPageNumber":1,"nextPage":2,"pageSize":10,"previousPage":0,"thisPageFirstElementNumber":1,"thisPageLastElementNumber":4,"totalElements":4}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @JsonInclude(Include.NON_NULL)
    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 5455109702966816368L;
        /**
         * curPage : 1
         * elements : [{"content":"本群须知20180613","createOn":1528857476000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":7,"isTop":1,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"测试公告20180613","updateOn":1528857476000,"userId":2,"userName":"李小二"},{"content":"此会议将于7月01日14：00准时召开，请做好准备！","createOn":1528697847000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":3,"isTop":1,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528697847000,"userId":1,"userName":"小塔米"},{"content":"本群须知","createOn":1528856114000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":6,"isTop":0,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528856114000,"userId":1,"userName":"小塔米"},{"content":"此会议将于6月15日10：00准时召开，请做好准备！","createOn":1528698140000,"groupType":1,"iconUrl":"https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg","id":4,"isTop":0,"meetingId":1,"noticeType":3,"status":0,"systemId":4,"title":"","updateOn":1528698140000,"userId":1,"userName":"小塔米"}]
         * firstPage : true
         * lastPage : true
         * lastPageNumber : 1
         * nextPage : 2
         * pageSize : 10
         * previousPage : 0
         * thisPageFirstElementNumber : 1
         * thisPageLastElementNumber : 4
         * totalElements : 4
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
        public static class ElementsBean implements Serializable{
            private static final long serialVersionUID = -6914692026051094731L;
            /**
             * content : 本群须知20180613
             * createOn : 1528857476000
             * groupType : 1
             * iconUrl : https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg
             * id : 7
             * isTop : 1
             * meetingId : 1
             * noticeType : 3
             * status : 0
             * systemId : 4
             * title : 测试公告20180613
             * updateOn : 1528857476000
             * userId : 2
             * userName : 李小二
             */

            private String content;
            private long createOn;
            private int groupType;
            private String iconUrl;
            private int id;
            private int isTop;
            private int meetingId;
            private int noticeType;
            private int status;
            private int systemId;
            private String title;
            private long updateOn;
            private int userId;
            private String userName;
            private String relativeDate;

            public String getRelativeDate() {
                return relativeDate;
            }

            public void setRelativeDate(String relativeDate) {
                this.relativeDate = relativeDate;
            }

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

            public int getGroupType() {
                return groupType;
            }

            public void setGroupType(int groupType) {
                this.groupType = groupType;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsTop() {
                return isTop;
            }

            public void setIsTop(int isTop) {
                this.isTop = isTop;
            }

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public int getNoticeType() {
                return noticeType;
            }

            public void setNoticeType(int noticeType) {
                this.noticeType = noticeType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getSystemId() {
                return systemId;
            }

            public void setSystemId(int systemId) {
                this.systemId = systemId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getUpdateOn() {
                return updateOn;
            }

            public void setUpdateOn(long updateOn) {
                this.updateOn = updateOn;
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
