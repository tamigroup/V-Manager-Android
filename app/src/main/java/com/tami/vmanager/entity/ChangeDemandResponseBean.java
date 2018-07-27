package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/10
 * 需求变化 活动变化 服务器返回实体类
 */
public class ChangeDemandResponseBean extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -910083653235874172L;

    /**
     * data : {"thisPageFirstElementNumber":1,"curPage":1,"previousPage":0,"thisPageLastElementNumber":1,"firstPage":true,"lastPage":true,"nextPage":2,"elements":[{"replyUserName":"","requestUserId":15201209183,"requestUserName":"159","meetingId":314,"requestIconUrl":"http://test.tamigroup.com/portrait/15201209183.png","requestTime":"7月27日 14:12","replyUserId":0,"requestOn":1532671925000,"replayIconUrl":"","replyOn":"","replyStatus":0,"createOn":1532671925000,"id":117,"replyContent":"","updateOn":1532671925000,"requestContent":"兔兔涂涂乐","status":0}],"pageSize":10,"lastPageNumber":1,"totalElements":1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * thisPageFirstElementNumber : 1
         * curPage : 1
         * previousPage : 0
         * thisPageLastElementNumber : 1
         * firstPage : true
         * lastPage : true
         * nextPage : 2
         * elements : [{"replyUserName":"","requestUserId":15201209183,"requestUserName":"159","meetingId":314,"requestIconUrl":"http://test.tamigroup.com/portrait/15201209183.png","requestTime":"7月27日 14:12","replyUserId":0,"requestOn":1532671925000,"replayIconUrl":"","replyOn":"","replyStatus":0,"createOn":1532671925000,"id":117,"replyContent":"","updateOn":1532671925000,"requestContent":"兔兔涂涂乐","status":0}]
         * pageSize : 10
         * lastPageNumber : 1
         * totalElements : 1
         */

        private int thisPageFirstElementNumber;
        private int curPage;
        private int previousPage;
        private int thisPageLastElementNumber;
        private boolean firstPage;
        private boolean lastPage;
        private int nextPage;
        private int pageSize;
        private int lastPageNumber;
        private int totalElements;
        private List<ElementsBean> elements;

        public int getThisPageFirstElementNumber() {
            return thisPageFirstElementNumber;
        }

        public void setThisPageFirstElementNumber(int thisPageFirstElementNumber) {
            this.thisPageFirstElementNumber = thisPageFirstElementNumber;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPreviousPage() {
            return previousPage;
        }

        public void setPreviousPage(int previousPage) {
            this.previousPage = previousPage;
        }

        public int getThisPageLastElementNumber() {
            return thisPageLastElementNumber;
        }

        public void setThisPageLastElementNumber(int thisPageLastElementNumber) {
            this.thisPageLastElementNumber = thisPageLastElementNumber;
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

        public int getLastPageNumber() {
            return lastPageNumber;
        }

        public void setLastPageNumber(int lastPageNumber) {
            this.lastPageNumber = lastPageNumber;
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

        public static class ElementsBean implements Serializable {
            /**
             * replyUserName :
             * requestUserId : 15201209183
             * requestUserName : 159
             * meetingId : 314
             * requestIconUrl : http://test.tamigroup.com/portrait/15201209183.png
             * requestTime : 7月27日 14:12
             * replyUserId : 0
             * requestOn : 1532671925000
             * replayIconUrl :
             * replyOn :
             * replyStatus : 0
             * createOn : 1532671925000
             * id : 117
             * replyContent :
             * updateOn : 1532671925000
             * requestContent : 兔兔涂涂乐
             * status : 0
             */

            private String replyUserName;
            private long requestUserId;
            private String requestUserName;
            private int meetingId;
            private String requestIconUrl;
            private String requestTime;
            private int replyUserId;
            private long requestOn;
            private String replayIconUrl;
            private String replyOn;
            private int replyStatus;
            private long createOn;
            private int id;
            private String replyContent;
            private long updateOn;
            private String requestContent;
            private int status;

            public String getReplyUserName() {
                return replyUserName;
            }

            public void setReplyUserName(String replyUserName) {
                this.replyUserName = replyUserName;
            }

            public long getRequestUserId() {
                return requestUserId;
            }

            public void setRequestUserId(long requestUserId) {
                this.requestUserId = requestUserId;
            }

            public String getRequestUserName() {
                return requestUserName;
            }

            public void setRequestUserName(String requestUserName) {
                this.requestUserName = requestUserName;
            }

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public String getRequestIconUrl() {
                return requestIconUrl;
            }

            public void setRequestIconUrl(String requestIconUrl) {
                this.requestIconUrl = requestIconUrl;
            }

            public String getRequestTime() {
                return requestTime;
            }

            public void setRequestTime(String requestTime) {
                this.requestTime = requestTime;
            }

            public int getReplyUserId() {
                return replyUserId;
            }

            public void setReplyUserId(int replyUserId) {
                this.replyUserId = replyUserId;
            }

            public long getRequestOn() {
                return requestOn;
            }

            public void setRequestOn(long requestOn) {
                this.requestOn = requestOn;
            }

            public String getReplayIconUrl() {
                return replayIconUrl;
            }

            public void setReplayIconUrl(String replayIconUrl) {
                this.replayIconUrl = replayIconUrl;
            }

            public String getReplyOn() {
                return replyOn;
            }

            public void setReplyOn(String replyOn) {
                this.replyOn = replyOn;
            }

            public int getReplyStatus() {
                return replyStatus;
            }

            public void setReplyStatus(int replyStatus) {
                this.replyStatus = replyStatus;
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

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
            }

            public long getUpdateOn() {
                return updateOn;
            }

            public void setUpdateOn(long updateOn) {
                this.updateOn = updateOn;
            }

            public String getRequestContent() {
                return requestContent;
            }

            public void setRequestContent(String requestContent) {
                this.requestContent = requestContent;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
