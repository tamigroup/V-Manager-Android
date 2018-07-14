package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取部门用户组.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetUserInDepartmentResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    public static final long serialVersionUID = 1L;


    public GetUserInDepartmentResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array {

        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item {

            public int id;	/*2*/
            public int orderBy;	/*2*/
            public int status;	/*0*/
            public String depName;	/*销售部*/
            public int systemId;	/*1*/
            public String createOn;	/*2018-05-22 07:51:28*/
            public String updateOn;	/*2018-05-22 07:51:31*/

            public List<User> userList;

            @JsonInclude(Include.NON_NULL)
            public static class User {

                public int status;	/*0*/
                public String password;	/*2f9a924bf41b5859fb044364a0539382*/
                public int depId;	/*2*/
                public int fromPlat;	/*1*/
                public int id;	/*6*/
                public String nickName;	/*rbq_nick*/
                public int positionId;	/*2*/
                public String realName;	/*宴会总监-rbq*/
                public int isAdmin;	/*1*/
                public int systemId;	/*4*/
                public String createOn;	/*2018-05-31 14:26:47*/
                public String updateOn;	/*2018-05-31 14:26:47*/
                public String mobile;	/*15117969341*/
                public int roleId;	/*2*/

                public boolean isSelected;
            }
        }
    }

}