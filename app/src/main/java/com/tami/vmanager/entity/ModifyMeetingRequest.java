package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;
import java.util.List;

/**
 * Created by why on 2018/7/13.
 */

public class ModifyMeetingRequest extends MobileMessage implements Serializable {

    private int meetingId;
    private int userId;
    private int systemId;//酒店ID
    private String meetingName;    //会议名称
    private String meetingAddressId;//会议厅ID
    private String startDate;//开始时间  "2018-06-20 00:00:00"
    private String endDate;//结束时间  "2018-06-20 00:00:00"
    private String estimateNum;//预计人数
    private String minNum;//保底人数
    private String sponsorName;//主办方名称
    private String isImportant;//VIP等级  0 ，1，2，3，4
    private String vipReceiveUserId;//接待人员ID集合 "1,2,3"
    private String eoUrl;//EO单地址
    private int isVzh;//是否V智会  0不是  1是
    private List<ModifyMeetingRequest.Item> vipList;//VIP参会人员LIST    [{"name":"王总",“intro”:""},{"name":"李总",“intro”:""}]

    public static class Item {
        private int systemId;
        private String name;
        private String intro;

        public int getSystemId() {
            return systemId;
        }

        public void setSystemId(int systemId) {
            this.systemId = systemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }


    public String getMeetingAddressId() {
        return meetingAddressId;
    }

    public void setMeetingAddressId(String meetingAddressId) {
        this.meetingAddressId = meetingAddressId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getEstimateNum() {
        return estimateNum;
    }

    public void setEstimateNum(String estimateNum) {
        this.estimateNum = estimateNum;
    }

    public String getMinNum() {
        return minNum;
    }

    public void setMinNum(String minNum) {
        this.minNum = minNum;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public String getIsImportant() {
        return isImportant;
    }

    public void setIsImportant(String isImportant) {
        this.isImportant = isImportant;
    }

    public String getVipReceiveUserId() {
        return vipReceiveUserId;
    }

    public void setVipReceiveUserId(String vipReceiveUserId) {
        this.vipReceiveUserId = vipReceiveUserId;
    }

    public String getEoUrl() {
        return eoUrl;
    }

    public void setEoUrl(String eoUrl) {
        this.eoUrl = eoUrl;
    }

    public int getIsVzh() {
        return isVzh;
    }

    public void setIsVzh(int isVzh) {
        this.isVzh = isVzh;
    }

    public List<Item> getVipList() {
        return vipList;
    }

    public void setVipList(List<Item> vipList) {
        this.vipList = vipList;
    }

    private String requestUrl;

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.UPDATE_MEETING;
    }

    @Override
    public Class getResponseClass() {
        return ModifyMeetingResponse.class;
    }

}
