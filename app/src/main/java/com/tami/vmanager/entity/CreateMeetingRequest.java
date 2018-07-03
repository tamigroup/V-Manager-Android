package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;
import java.util.List;


/**
 * 创建会议.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class CreateMeetingRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CreateMeetingRequest() {
        super();
    }

    private String systemId;//酒店ID
    private String meetingName;    //会议名称
    private String createMeetingUserId;//创建者ID
    private String meetingAddressId;//会议厅ID
    private String startDate;//开始时间  "2018-06-20 00:00:00"
    private String endDate;//结束时间  "2018-06-20 00:00:00"
    private String contractMoney;//合同金额
    private String payMoney;//已付金额
    private String estimateNum;//预计人数
    private String minNum;//保底人数
    private String sponsorName;//主办方名称
    private String isImportant;//VIP等级  0 ，1，2，3，4
    private String vipReceiveUserId;//接待人员ID集合 "1,2,3"
    private String eoUrl;//EO单地址
    private String isVzh;//是否V智会  0不是  1是
    private List<Item> vipList;//VIP参会人员LIST    [{"name":"王总",“intro”:""},{"name":"李总",“intro”:""}]

    public static class Item {
        private String systemId;
        private String name;
        private String intro;

        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        public String getName(String name) {
            return this.name;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getCreateMeetingUserId() {
        return createMeetingUserId;
    }

    public void setCreateMeetingUserId(String createMeetingUserId) {
        this.createMeetingUserId = createMeetingUserId;
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

    public String getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(String contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
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

    public String getIsVzh() {
        return isVzh;
    }

    public void setIsVzh(String isVzh) {
        this.isVzh = isVzh;
    }

    public List<Item> getVipList() {
        return vipList;
    }

    public void setVipList(List<Item> vipList) {
        this.vipList = vipList;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.CREATE_MEETING;
    }

    @Override
    public Class getResponseClass() {
        return CreateMeetingResponse.class;
    }
}