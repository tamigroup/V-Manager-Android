package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/6
 * 意见箱 评论列表 服务器返回
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EvaluatePageResponseBean extends MobileMessage implements Serializable{

    private static final long serialVersionUID = 2738785611200411111L;

    /**
     * data : {"curPage":1,"elements":[{"anonymous":0,"content":"拉几","createOn":1532672217000,"flag":1,"iconUrl":"http://test.tamigroup.com/portrait/15201209183.png","id":155,"meetingId":314,"score":5,"status":0,"type":2,"updateOn":1532672217000,"userId":15201209183,"userName":"159"}],"firstPage":true,"lastPage":true,"lastPageNumber":1,"nextPage":2,"pageSize":10,"previousPage":0,"thisPageFirstElementNumber":1,"thisPageLastElementNumber":1,"totalElements":1}
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
         * curPage : 1
         * elements : [{"anonymous":0,"content":"拉几","createOn":1532672217000,"flag":1,"iconUrl":"http://test.tamigroup.com/portrait/15201209183.png","id":155,"meetingId":314,"score":5,"status":0,"type":2,"updateOn":1532672217000,"userId":15201209183,"userName":"159"}]
         * firstPage : true
         * lastPage : true
         * lastPageNumber : 1
         * nextPage : 2
         * pageSize : 10
         * previousPage : 0
         * thisPageFirstElementNumber : 1
         * thisPageLastElementNumber : 1
         * totalElements : 1
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
            /**
             * anonymous : 0   0是不匿名 1是匿名
             * content : 拉几
             * createOn : 1532672217000
             * flag : 1
             * iconUrl : http://test.tamigroup.com/portrait/15201209183.png
             * id : 155
             * meetingId : 314
             * score : 5
             * status : 0
             * type : 2
             * updateOn : 1532672217000
             * userId : 15201209183
             * userName : 159
             */

            private int anonymous;
            private String content;
            private long createOn;
            private int flag;
            private String iconUrl;
            private int id;
            private int meetingId;
            private int score;
            private int status;
            private int type;
            private long updateOn;
            private long userId;
            private String userName;

            public int getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(int anonymous) {
                this.anonymous = anonymous;
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

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
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

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public long getUpdateOn() {
                return updateOn;
            }

            public void setUpdateOn(long updateOn) {
                this.updateOn = updateOn;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
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
