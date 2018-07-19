package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取(合同金额/已举办/待举办).服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetBannerDataResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GetBannerDataResponse() {
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

        private String unPayCount;	/*8500.00*/
        private int allMeetingCount;	/*13*/
        private int overMeetingCount;	/*11*/
        private String allPayCount;	/*18000.00*/
        private int waitMeetingCount;	/*2*/

        public String getUnPayCount() {
            return unPayCount;
        }

        public void setUnPayCount(String unPayCount) {
            this.unPayCount = unPayCount;
        }

        public int getAllMeetingCount() {
            return allMeetingCount;
        }

        public void setAllMeetingCount(int allMeetingCount) {
            this.allMeetingCount = allMeetingCount;
        }

        public int getOverMeetingCount() {
            return overMeetingCount;
        }

        public void setOverMeetingCount(int overMeetingCount) {
            this.overMeetingCount = overMeetingCount;
        }

        public String getAllPayCount() {
            return allPayCount;
        }

        public void setAllPayCount(String allPayCount) {
            this.allPayCount = allPayCount;
        }

        public int getWaitMeetingCount() {
            return waitMeetingCount;
        }

        public void setWaitMeetingCount(int waitMeetingCount) {
            this.waitMeetingCount = waitMeetingCount;
        }
    }
}