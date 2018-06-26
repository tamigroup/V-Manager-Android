package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取首页会议总计.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetIndexResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    public GetIndexResponse() {
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

        private String myFollowMeetingsCount;
        private String todayMeetingsCount;
        private String waitMeetingsCount;

        /**
         * @return
         */
        public String getMyFollowMeetingsCount() {
            return myFollowMeetingsCount;
        }

        public void setMyFollowMeetingsCount(String myFollowMeetingsCount) {
            this.myFollowMeetingsCount = myFollowMeetingsCount;
        }

        /**
         * @return
         */
        public String getTodayMeetingsCount() {
            return todayMeetingsCount;
        }

        public void setTodayMeetingsCount(String todayMeetingsCount) {
            this.todayMeetingsCount = todayMeetingsCount;
        }

        /**
         * @return
         */
        public String getWaitMeetingsCount() {
            return waitMeetingsCount;
        }

        public void setWaitMeetingsCount(String waitMeetingsCount) {
            this.waitMeetingsCount = waitMeetingsCount;
        }
    }
}