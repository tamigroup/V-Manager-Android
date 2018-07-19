package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.List;

/**
 * Created by why on 2018/7/14.
 */
@JsonInclude(Include.NON_NULL)
public class GetSelectMeetingItemsUserResponse extends MobileMessage implements Serializable {

    public static final long serialVersionUID = -714898414343136695L;

    public GetSelectMeetingItemsUserResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable{

        private static final long serialVersionUID = -3904597215046370302L;
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable{
            private static final long serialVersionUID = -6412385436752613836L;
            public String registrationId;	/*1104a89792fa59a83a2*/
            public int status;	/*0*/
            public String iconUrl;	/*http://f.tamiyun.com/app/af20180714143903_25139.png*/
            public String systemName;	/**/
            public String depName;	/**/
            public int depId;	/*5*/
            public String password;	/*a26733af21fb5aa912fab6ce537e43d7*/
            public int fromPlat;	/*1*/
            public int id;	/*1*/
            public String nickName;	/*宁涛*/
            public String token;	/**/
            public int positionId;	/*6*/
            public String deviceToken;	/**/
            public int isAdmin;	/*1*/
            public String realName;	/*小塔米*/
            public int systemId;	/*4*/
            public String userRoleList;	/**/
            public String positionName;	/*研发部*/
            public Long createOn;	/*1526946603000*/
            public Long updateOn;	/*1531550344000*/
            public int roleId;	/*2*/
            public String mobile;	/*13800138000*/
        }
    }
}
