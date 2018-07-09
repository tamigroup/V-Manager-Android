package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取会议群组中的用户成员.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class MeetingUserGroupPageResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    public static final long serialVersionUID = 1L;

    public MeetingUserGroupPageResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array {
        @JsonInclude(Include.NON_NULL)
        public static class Item {
            public String registrationId;	/*1104a89792fa59a83a2*/
            public int status;	/*0*/
            public String iconUrl;	/*https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg*/
            public String systemName;	/**/
            public String depName;	/**/
            public int depId;	/*5*/
            public String password;	/*3f57efeb8c91c6bae91ccd6e8790f8ad*/
            public int fromPlat;	/*1*/
            public int id;	/*1*/
            public String nickName;	/*宁涛*/
            public String token;	/**/
            public int positionId;	/*6*/
            public int isAdmin;	/*1*/
            public String realName;	/*小塔米*/
            public int systemId;	/*4*/
            public String userRoleList;	/**/
            public String positionName;	/**/
            public Long createOn;	/*1526946603000*/
            public Long updateOn;	/*1530517514000*/
            public int roleId;	/*2*/
            public String mobile;	/*13800138000*/
        }

        public List<Item> elements;	/*List<TElements>*/

        public boolean lastPage;	/*true*/
        public int nextPage;	/*2*/
        public int curPage;	/*1*/
        public int totalElements;	/*2*/
        public int pageSize;	/*10*/
        public int lastPageNumber;	/*1*/
        public boolean firstPage;	/*true*/
        public int thisPageLastElementNumber;	/*2*/
        public int thisPageFirstElementNumber;	/*1*/
        public int previousPage;	/*0*/
    }
}