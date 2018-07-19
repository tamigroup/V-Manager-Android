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
     * data : {"curPage":1,"elements":[{"createOn":1531119706000,"id":42,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"哦婆婆娜","requestIconUrl":"","requestOn":1531119706000,"requestTime":"7月9日 15:01","requestUserId":12,"requestUserName":"","status":0,"updateOn":1531119706000},{"createOn":1531119592000,"id":41,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"123","requestIconUrl":"","requestOn":1531119592000,"requestTime":"7月9日 14:59","requestUserId":12,"requestUserName":"","status":0,"updateOn":1531119592000},{"createOn":1530868891000,"id":40,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"还是说呢","requestIconUrl":"","requestOn":1530868891000,"requestTime":"7月6日 17:21","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530868891000},{"createOn":1530673431000,"id":39,"meetingId":6,"replayIconUrl":"","replyContent":"谢谢您的评价","replyOn":1530756117000,"replyStatus":1,"replyUserId":1,"replyUserName":"武装部-小塔米","requestContent":"赶快来参加抽奖活动吧","requestIconUrl":"http://pre.tamigroup.com/portrait/15712964872.png","requestOn":1530673431000,"requestTime":"7月4日 11:03","requestUserId":12,"requestUserName":"15712964872","status":0,"updateOn":1530756117000},{"createOn":1530255752000,"id":38,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"Hh","requestIconUrl":"","requestOn":1530255752000,"requestTime":"6月29日 15:02","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530255752000},{"createOn":1530252597000,"id":37,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"兔兔","requestIconUrl":"","requestOn":1530252597000,"requestTime":"6月29日 14:09","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530252597000},{"createOn":1530251257000,"id":36,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"Hi","requestIconUrl":"","requestOn":1530251257000,"requestTime":"6月29日 13:47","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530251257000},{"createOn":1530244697000,"id":35,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"你咋滴","requestIconUrl":"","requestOn":1530244697000,"requestTime":"6月29日 11:58","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530244697000},{"createOn":1530243632000,"id":34,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"兔兔","requestIconUrl":"","requestOn":1530243632000,"requestTime":"6月29日 11:40","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530243632000},{"createOn":1530178023000,"id":33,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"会议马上开始","requestIconUrl":"http://test.tamigroup.com/portrait/15712964872.png","requestOn":1530178023000,"requestTime":"6月28日 17:27","requestUserId":12,"requestUserName":"15712964872","status":0,"updateOn":1530178023000}],"firstPage":true,"lastPage":false,"lastPageNumber":3,"nextPage":2,"pageSize":10,"previousPage":0,"thisPageFirstElementNumber":1,"thisPageLastElementNumber":10,"totalElements":22}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements  Serializable{
        private static final long serialVersionUID = -3308398941395112559L;
        /**
         * curPage : 1
         * elements : [{"createOn":1531119706000,"id":42,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"哦婆婆娜","requestIconUrl":"","requestOn":1531119706000,"requestTime":"7月9日 15:01","requestUserId":12,"requestUserName":"","status":0,"updateOn":1531119706000},{"createOn":1531119592000,"id":41,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"123","requestIconUrl":"","requestOn":1531119592000,"requestTime":"7月9日 14:59","requestUserId":12,"requestUserName":"","status":0,"updateOn":1531119592000},{"createOn":1530868891000,"id":40,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"还是说呢","requestIconUrl":"","requestOn":1530868891000,"requestTime":"7月6日 17:21","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530868891000},{"createOn":1530673431000,"id":39,"meetingId":6,"replayIconUrl":"","replyContent":"谢谢您的评价","replyOn":1530756117000,"replyStatus":1,"replyUserId":1,"replyUserName":"武装部-小塔米","requestContent":"赶快来参加抽奖活动吧","requestIconUrl":"http://pre.tamigroup.com/portrait/15712964872.png","requestOn":1530673431000,"requestTime":"7月4日 11:03","requestUserId":12,"requestUserName":"15712964872","status":0,"updateOn":1530756117000},{"createOn":1530255752000,"id":38,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"Hh","requestIconUrl":"","requestOn":1530255752000,"requestTime":"6月29日 15:02","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530255752000},{"createOn":1530252597000,"id":37,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"兔兔","requestIconUrl":"","requestOn":1530252597000,"requestTime":"6月29日 14:09","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530252597000},{"createOn":1530251257000,"id":36,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"Hi","requestIconUrl":"","requestOn":1530251257000,"requestTime":"6月29日 13:47","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530251257000},{"createOn":1530244697000,"id":35,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"你咋滴","requestIconUrl":"","requestOn":1530244697000,"requestTime":"6月29日 11:58","requestUserId":12,"requestUserName":"1369922467","status":0,"updateOn":1530244697000},{"createOn":1530243632000,"id":34,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"兔兔","requestIconUrl":"","requestOn":1530243632000,"requestTime":"6月29日 11:40","requestUserId":12,"requestUserName":"","status":0,"updateOn":1530243632000},{"createOn":1530178023000,"id":33,"meetingId":6,"replayIconUrl":"","replyContent":"","replyOn":"","replyStatus":0,"replyUserId":0,"replyUserName":"","requestContent":"会议马上开始","requestIconUrl":"http://test.tamigroup.com/portrait/15712964872.png","requestOn":1530178023000,"requestTime":"6月28日 17:27","requestUserId":12,"requestUserName":"15712964872","status":0,"updateOn":1530178023000}]
         * firstPage : true
         * lastPage : false
         * lastPageNumber : 3
         * nextPage : 2
         * pageSize : 10
         * previousPage : 0
         * thisPageFirstElementNumber : 1
         * thisPageLastElementNumber : 10
         * totalElements : 22
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

        public static class ElementsBean implements Serializable{
            private static final long serialVersionUID = 3697506379734144902L;
            /**
             * createOn : 1531119706000
             * id : 42
             * meetingId : 6
             * replayIconUrl :
             * replyContent :
             * replyOn :
             * replyStatus : 0
             * replyUserId : 0
             * replyUserName :
             * requestContent : 哦婆婆娜
             * requestIconUrl :
             * requestOn : 1531119706000
             * requestTime : 7月9日 15:01
             * requestUserId : 12
             * requestUserName :
             * status : 0
             * updateOn : 1531119706000
             */

            private long createOn;
            private int id;
            private int meetingId;
            private String replayIconUrl;
            private String replyContent;
            private String replyOn;
            private int replyStatus;
            private int replyUserId;
            private String replyUserName;
            private String requestContent;
            private String requestIconUrl;
            private long requestOn;
            private String requestTime;
            private int requestUserId;
            private String requestUserName;
            private int status;
            private long updateOn;

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

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public String getReplayIconUrl() {
                return replayIconUrl;
            }

            public void setReplayIconUrl(String replayIconUrl) {
                this.replayIconUrl = replayIconUrl;
            }

            public String getReplyContent() {
                return replyContent;
            }

            public void setReplyContent(String replyContent) {
                this.replyContent = replyContent;
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

            public int getReplyUserId() {
                return replyUserId;
            }

            public void setReplyUserId(int replyUserId) {
                this.replyUserId = replyUserId;
            }

            public String getReplyUserName() {
                return replyUserName;
            }

            public void setReplyUserName(String replyUserName) {
                this.replyUserName = replyUserName;
            }

            public String getRequestContent() {
                return requestContent;
            }

            public void setRequestContent(String requestContent) {
                this.requestContent = requestContent;
            }

            public String getRequestIconUrl() {
                return requestIconUrl;
            }

            public void setRequestIconUrl(String requestIconUrl) {
                this.requestIconUrl = requestIconUrl;
            }

            public long getRequestOn() {
                return requestOn;
            }

            public void setRequestOn(long requestOn) {
                this.requestOn = requestOn;
            }

            public String getRequestTime() {
                return requestTime;
            }

            public void setRequestTime(String requestTime) {
                this.requestTime = requestTime;
            }

            public int getRequestUserId() {
                return requestUserId;
            }

            public void setRequestUserId(int requestUserId) {
                this.requestUserId = requestUserId;
            }

            public String getRequestUserName() {
                return requestUserName;
            }

            public void setRequestUserName(String requestUserName) {
                this.requestUserName = requestUserName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getUpdateOn() {
                return updateOn;
            }

            public void setUpdateOn(long updateOn) {
                this.updateOn = updateOn;
            }
        }
    }
}
