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


    private static final long serialVersionUID = 6475363159500735904L;

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

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable{

        private static final long serialVersionUID = -7030205762390034661L;
        private int myFollowMeetingsCount;
        private int todayMeetingsCount;
        private int waitMeetingsCount;

        public int getMyFollowMeetingsCount() {
            return myFollowMeetingsCount;
        }

        public void setMyFollowMeetingsCount(int myFollowMeetingsCount) {
            this.myFollowMeetingsCount = myFollowMeetingsCount;
        }

        public int getTodayMeetingsCount() {
            return todayMeetingsCount;
        }

        public void setTodayMeetingsCount(int todayMeetingsCount) {
            this.todayMeetingsCount = todayMeetingsCount;
        }

        public int getWaitMeetingsCount() {
            return waitMeetingsCount;
        }

        public void setWaitMeetingsCount(int waitMeetingsCount) {
            this.waitMeetingsCount = waitMeetingsCount;
        }
    }
}