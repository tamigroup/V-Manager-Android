package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 修改用户头像.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class UpdateUserIconResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 4702271919249650288L;

    public UpdateUserIconResponse() {
        super();
    }

    public Item data;

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable{

        private static final long serialVersionUID = -3062477343077955487L;
        public String registrationId;	/**/
        public int status;	/*0*/
        public String iconUrl;	/*http://f.tamiyun.com/app/af20180712103150_93219.jpg*/
        public String systemName;	/**/
        public String depName;	/**/
        public int depId;	/*6*/
        public String password;	/*3f57efeb8c91c6bae91ccd6e8790f8ad*/
        public int fromPlat;	/*0*/
        public int id;	/*33*/
        public String nickName;	/*李双希*/
        public String token;	/**/
        public int positionId;	/*6*/
        public String realName;	/*李双希*/
        public int isAdmin;	/*0*/
        public int systemId;	/*4*/
        public String positionName;	/**/
        public Long createOn;	/*1529907675000*/
        public String mobile;	/*15901125418*/
        public int roleId;	/*0*/
        public Long updateOn;	/*1531362714038*/
//        public List<ElementUserRoleList> userRoleList = new ArrayList<>();	/*String*/


//        @JsonInclude(Include.NON_NULL)
//        public static class ElementUserRoleList {
//
//            public int id;	/*1*/
//            public int status;	/*0*/
//            public int userId;	/*1*/
//            public String createOn;	/*2018-05-22 08:17:24*/
//            public String updateOn;	/*2018-05-22 08:17:22*/
//            public int roleId;	/*2*/
//        }
    }

}