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

    public class Item {
        private String allMeetingCount;
        private String allPayCount;
        private String overMeetingCount;
        private String unPayCount;

        /**
         * @return
         */
        public String getAllMeetingCount() {
            return allMeetingCount;
        }

        public void setAllMeetingCount(String allMeetingCount) {
            this.allMeetingCount = allMeetingCount;
        }

        /**
         * @return
         */
        public String getAllPayCount() {
            return allPayCount;
        }

        public void setAllPayCount(String allPayCount) {
            this.allPayCount = allPayCount;
        }

        /**
         * @return
         */
        public String getOverMeetingCount() {
            return overMeetingCount;
        }

        public void setOverMeetingCount(String overMeetingCount) {
            this.overMeetingCount = overMeetingCount;
        }

        /**
         * @return
         */
        public String getUnPayCount() {
            return unPayCount;
        }

        public void setUnPayCount(String unPayCount) {
            this.unPayCount = unPayCount;
        }
    }
}