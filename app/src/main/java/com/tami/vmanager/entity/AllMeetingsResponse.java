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
    public static final long serialVersionUID = 1L;

    public AllMeetingsResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array {

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable {

            public static final long serialVersionUID = 8899967346975287665L;
            public int saleUserId;	/*1*/
            public String saleUserName;	/*小塔米*/
            public String meetingAddress;	/*满月楼*/
            public int actualNum;	/*0*/
            public String eoUrl;	/*http://vgj.oss-cn-beijing.aliyuncs.com/app/af20180704180454_87882.png*/
            public String isPay;	/*待付款*/
            public String perfectStatus;	/*待完善*/
            public int estimateNum;	/*300*/
            public int meetingAddressId;	/*2*/
            public Long endTime;	/*1531742400000*/
            public int minNum;	/*280*/
            public int fromPlat;	/*2*/
            public int isImportant;	/*4*/
            public Long startTime;	/*1531612800000*/
            public int followStatus;	/*0*/
            public String meetingName;	/*测试会议rbq20180705*/
            public String sponsorName;	/**/
            public String meetingStatus;	/*待执行*/
            public String autoDayTime;	/*7月15日08:00~7月16日20:00*/
            public int meetingId;	/*32*/
            public String salesUserMobile;	/*13800138000*/
            public int vzhStatus;	/*1*/

        }

        public List<Item> elements;

        public boolean lastPage;	/*false*/
        public int nextPage;	/*2*/
        public int curPage;	/*1*/
        public int totalElements;	/*17*/
        public int pageSize;	/*10*/
        public int lastPageNumber;	/*2*/
        public boolean firstPage;	/*true*/
        public int thisPageLastElementNumber;	/*10*/
        public int thisPageFirstElementNumber;	/*1*/
        public int previousPage;	/*0*/
    }
}