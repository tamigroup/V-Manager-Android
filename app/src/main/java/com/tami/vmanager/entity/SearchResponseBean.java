package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/4
 * 从服务器获取的数据
 */
@JsonInclude(Include.NON_NULL)
public class SearchResponseBean extends MobileMessage implements Serializable {

    public static final long serialVersionUID = 1L;

    public SearchResponseBean() {
        super();
    }

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @JsonInclude(Include.NON_NULL)
    public static class DataBean {
        /**
         * actualNum : 0
         * autoDayTime : 6月8日08:40~6月8日18:40
         * endTime : 1528454430000
         * eoUrl :
         * estimateNum : 350
         * followStatus : false
         * fromPlat : 2
         * isImportant : 1
         * isPay :
         * meetingAddress : 火星子串吧
         * meetingAddressId : 5
         * meetingId : 1
         * meetingName : 塔米G100峰会
         * meetingStatus : 已结束
         * minNum : 300
         * perfectStatus :
         * saleUserId : 1
         * saleUserName : 小塔米
         * sponsorName :
         * startTime : 1528418419000
         * vzhStatus : 0
         */

        private int actualNum;
        private String autoDayTime;
        private long endTime;
        private String eoUrl;
        private int estimateNum;
        private boolean followStatus;
        private int fromPlat;
        private int isImportant;
        private String isPay;
        private String meetingAddress;
        private int meetingAddressId;
        private int meetingId;
        private String meetingName;
        private String meetingStatus;
        private int minNum;
        private String perfectStatus;
        private int saleUserId;
        private String saleUserName;
        private String sponsorName;
        private long startTime;
        private int vzhStatus;

        public int getActualNum() {
            return actualNum;
        }

        public void setActualNum(int actualNum) {
            this.actualNum = actualNum;
        }

        public String getAutoDayTime() {
            return autoDayTime;
        }

        public void setAutoDayTime(String autoDayTime) {
            this.autoDayTime = autoDayTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getEoUrl() {
            return eoUrl;
        }

        public void setEoUrl(String eoUrl) {
            this.eoUrl = eoUrl;
        }

        public int getEstimateNum() {
            return estimateNum;
        }

        public void setEstimateNum(int estimateNum) {
            this.estimateNum = estimateNum;
        }

        public boolean isFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(boolean followStatus) {
            this.followStatus = followStatus;
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

        public String getIsPay() {
            return isPay;
        }

        public void setIsPay(String isPay) {
            this.isPay = isPay;
        }

        public String getMeetingAddress() {
            return meetingAddress;
        }

        public void setMeetingAddress(String meetingAddress) {
            this.meetingAddress = meetingAddress;
        }

        public int getMeetingAddressId() {
            return meetingAddressId;
        }

        public void setMeetingAddressId(int meetingAddressId) {
            this.meetingAddressId = meetingAddressId;
        }

        public int getMeetingId() {
            return meetingId;
        }

        public void setMeetingId(int meetingId) {
            this.meetingId = meetingId;
        }

        public String getMeetingName() {
            return meetingName;
        }

        public void setMeetingName(String meetingName) {
            this.meetingName = meetingName;
        }

        public String getMeetingStatus() {
            return meetingStatus;
        }

        public void setMeetingStatus(String meetingStatus) {
            this.meetingStatus = meetingStatus;
        }

        public int getMinNum() {
            return minNum;
        }

        public void setMinNum(int minNum) {
            this.minNum = minNum;
        }

        public String getPerfectStatus() {
            return perfectStatus;
        }

        public void setPerfectStatus(String perfectStatus) {
            this.perfectStatus = perfectStatus;
        }

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

        public String getSponsorName() {
            return sponsorName;
        }

        public void setSponsorName(String sponsorName) {
            this.sponsorName = sponsorName;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getVzhStatus() {
            return vzhStatus;
        }

        public void setVzhStatus(int vzhStatus) {
            this.vzhStatus = vzhStatus;
        }
    }
}