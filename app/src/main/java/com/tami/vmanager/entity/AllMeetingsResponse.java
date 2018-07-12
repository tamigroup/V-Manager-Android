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
            public	int	saleUserId;	/*1*/
            public	String	saleUserName;	/*小塔米*/
            public	int	actualNum;	/*0*/
            public	String	vipReceiveUser;	/**/
            public	String	perfectStatus;	/*待完善*/
            public	int	estimateNum;	/*1*/
            public	int	meetingAddressId;	/*41*/
            public	Long	endTime;	/*1531365960000*/
            public	int	isImportant;	/*0*/
            public	Long	startTime;	/*1531365960000*/
            public	String	meetingName;	/*T*/
            public	String	sponsorName;	/**/
            public	String	meetingStatus;	/*已结束*/
            public	String	autoDayTime;	/*7月12日11:26~7月12日11:26*/
            public	String	cancelStatus;	/**/
            public	String	meetingAddress;	/*餐厅*/
            public	String	eoUrl;	/*http://f.tamiyun.com/app/af20180712112708_32380.png*/
            public	String	isPay;	/**/
            public	int	minNum;	/*1*/
            public	int	fromPlat;	/*2*/
            public	int	followStatus;	/*0*/
            public	int	meetingId;	/*66*/
            public	String	vipGuestList;	/**/
            public	String	salesUserMobile;	/*13800138000*/
            public	int	vzhStatus;	/*1*/

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