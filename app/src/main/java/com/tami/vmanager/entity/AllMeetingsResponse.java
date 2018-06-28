package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询全部会议列表.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class AllMeetingsResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public AllMeetingsResponse() {
        super();
    }

    private Item data;

    public Item getData() {
        return data;
    }

    public void setData(Item data) {
        this.data = data;
    }

    @JsonInclude(Include.NON_NULL)
    public static class Item {

        /**
         * @see com.bwapi.TM.message.entity.AllMeetingsResponse#getElements
         */
        @JsonInclude(Include.NON_NULL)
        public static class ElementElements {

            private	int	saleUserId;	/*1*/
            private	String	saleUserName;	/*小塔米*/
            private	String	meetingAddress;	/*满月楼*/
            private	int	actualNum;	/*0*/
            private	String	eoUrl;	/**/
            private	String	isPay;	/*待付款*/
            private	String	perfectStatus;	/*待完善*/
            private	int	estimateNum;	/*200*/
            private	int	meetingAddressId;	/*2*/
            private	Long	endTime;	/*1530316800000*/
            private	int	minNum;	/*180*/
            private	int	fromPlat;	/*2*/
            private	int	isImportant;	/*3*/
            private	Long	startTime;	/*1530144000000*/
            private	Boolean	followStatus;	/*false*/
            private	String	meetingName;	/*王者大会5*/
            private	Object	sponsorName;	/*Object*/
            private	String	meetingStatus;	/*会前*/
            private	String	autoDayTime;	/*6月28日08:00~6月30日08:00*/
            private	int	meetingId;	/*19*/
            private	int	vzhStatus;	/*1*/

            public int getSaleUserId() {
                return saleUserId;
            }

            public void setSaleUserId(int saleUserId) {
                this.saleUserId = saleUserId;
            }

            public String getSaleUserName() {
                return saleUserName;
            }

            public void setSaleUserName(String saleUserName) {
                this.saleUserName = saleUserName;
            }

            public String getMeetingAddress() {
                return meetingAddress;
            }

            public void setMeetingAddress(String meetingAddress) {
                this.meetingAddress = meetingAddress;
            }

            public int getActualNum() {
                return actualNum;
            }

            public void setActualNum(int actualNum) {
                this.actualNum = actualNum;
            }

            public String getEoUrl() {
                return eoUrl;
            }

            public void setEoUrl(String eoUrl) {
                this.eoUrl = eoUrl;
            }

            public String getIsPay() {
                return isPay;
            }

            public void setIsPay(String isPay) {
                this.isPay = isPay;
            }

            public String getPerfectStatus() {
                return perfectStatus;
            }

            public void setPerfectStatus(String perfectStatus) {
                this.perfectStatus = perfectStatus;
            }

            public int getEstimateNum() {
                return estimateNum;
            }

            public void setEstimateNum(int estimateNum) {
                this.estimateNum = estimateNum;
            }

            public int getMeetingAddressId() {
                return meetingAddressId;
            }

            public void setMeetingAddressId(int meetingAddressId) {
                this.meetingAddressId = meetingAddressId;
            }

            public Long getEndTime() {
                return endTime;
            }

            public void setEndTime(Long endTime) {
                this.endTime = endTime;
            }

            public int getMinNum() {
                return minNum;
            }

            public void setMinNum(int minNum) {
                this.minNum = minNum;
            }

            public int getFromPlat() {
                return fromPlat;
            }

            public void setFromPlat(int fromPlat) {
                this.fromPlat = fromPlat;
            }

            public int getIsImportant() {
                return isImportant;
            }

            public void setIsImportant(int isImportant) {
                this.isImportant = isImportant;
            }

            public Long getStartTime() {
                return startTime;
            }

            public void setStartTime(Long startTime) {
                this.startTime = startTime;
            }

            public Boolean getFollowStatus() {
                return followStatus;
            }

            public void setFollowStatus(Boolean followStatus) {
                this.followStatus = followStatus;
            }

            public String getMeetingName() {
                return meetingName;
            }

            public void setMeetingName(String meetingName) {
                this.meetingName = meetingName;
            }

            public Object getSponsorName() {
                return sponsorName;
            }

            public void setSponsorName(Object sponsorName) {
                this.sponsorName = sponsorName;
            }

            public String getMeetingStatus() {
                return meetingStatus;
            }

            public void setMeetingStatus(String meetingStatus) {
                this.meetingStatus = meetingStatus;
            }

            public String getAutoDayTime() {
                return autoDayTime;
            }

            public void setAutoDayTime(String autoDayTime) {
                this.autoDayTime = autoDayTime;
            }

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            public int getVzhStatus() {
                return vzhStatus;
            }

            public void setVzhStatus(int vzhStatus) {
                this.vzhStatus = vzhStatus;
            }
        }

        private List<ElementElements> elements;

        /**
         * @return
         */
        public List<ElementElements> getElements() {
            return elements;
        }

        public void setElements(List<ElementElements> elements) {
            this.elements = elements;
        }

        private Boolean lastPage;	/*false*/
        private int nextPage;	/*2*/
        private int curPage;	/*1*/
        private int totalElements;	/*17*/
        private int pageSize;	/*10*/
        private int lastPageNumber;	/*2*/
        private Boolean firstPage;	/*true*/
        private int thisPageLastElementNumber;	/*10*/
        private int thisPageFirstElementNumber;	/*1*/
        private int previousPage;	/*0*/

        public Boolean getLastPage() {
            return lastPage;
        }

        public void setLastPage(Boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getNextPage() {
            return nextPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
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

        public Boolean getFirstPage() {
            return firstPage;
        }

        public void setFirstPage(Boolean firstPage) {
            this.firstPage = firstPage;
        }

        public int getThisPageLastElementNumber() {
            return thisPageLastElementNumber;
        }

        public void setThisPageLastElementNumber(int thisPageLastElementNumber) {
            this.thisPageLastElementNumber = thisPageLastElementNumber;
        }

        public int getThisPageFirstElementNumber() {
            return thisPageFirstElementNumber;
        }

        public void setThisPageFirstElementNumber(int thisPageFirstElementNumber) {
            this.thisPageFirstElementNumber = thisPageFirstElementNumber;
        }

        public int getPreviousPage() {
            return previousPage;
        }

        public void setPreviousPage(int previousPage) {
            this.previousPage = previousPage;
        }
    }
}