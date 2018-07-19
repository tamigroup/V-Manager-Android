package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 根据酒店ID查询会议厅List.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingAddressListResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = -4822449047388778273L;

    public GetMeetingAddressListResponse() {
        super();
    }

    public Array data;

    public static class Array implements Serializable{

        private static final long serialVersionUID = 387619787178242315L;
        public List<Item> dataList;

        /**
         */
        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable {

            private static final long serialVersionUID = -5431030678043923508L;
            public int id;	/*3*/
            public String position;	/*Object*/
            public int area;	/*0*/
            public int orderBy;	/*2*/
            public int status;	/*0*/
            public String name;	/*醉仙翁*/
            public String mark;	/*喝酒的地方*/
            public int systemId;	/*4*/
            public int maxNum;	/*0*/
            public Long createOn;	/*1528107162000*/
            public Long updateOn;	/*1528107166000*/

            public boolean isState;
        }
    }

}