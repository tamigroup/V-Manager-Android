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
@Deprecated
public class GetUserInDepartmentResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 9101313760090883038L;

    public GetUserInDepartmentResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable{

        private static final long serialVersionUID = -5424005008045365609L;
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable{

            private static final long serialVersionUID = -1485846386168772601L;
            public int id;	/*2*/
            public int orderBy;	/*2*/
            public int status;	/*0*/
            public String depName;	/*销售部*/
            public int systemId;	/*1*/
            public String createOn;	/*2018-05-22 07:51:28*/
            public String updateOn;	/*2018-05-22 07:51:31*/

            public List<User> userList;

            @JsonInclude(Include.NON_NULL)
            public static class User implements Serializable{

                private static final long serialVersionUID = -6243668584015439312L;
                public	String	registrationId;	/*1104a89792fa59a83a2*/
                public	int	status;	/*0*/
                public	String	iconUrl;	/*http://f.tamiyun.com/app/af20180714143903_25139.png*/
                public	String	systemName;	/**/
                public	String	depName;	/**/
                public	int	depId;	/*5*/
                public	String	password;	/*a26733af21fb5aa912fab6ce537e43d7*/
                public	int	fromPlat;	/*1*/
                public	int	id;	/*1*/
                public	String	nickName;	/*宁涛*/
                public	String	token;	/**/
                public	int	positionId;	/*6*/
                public	String	deviceToken;	/**/
                public	int	isAdmin;	/*1*/
                public	String	realName;	/*小塔米*/
                public	int	systemId;	/*4*/
                public	String	userRoleList;	/**/
                public	String	positionName;	/*研发部*/
                public	Long	createOn;	/*1526946603000*/
                public	Long	updateOn;	/*1531550344000*/
                public	int	roleId;	/*2*/
                public	String	mobile;	/*13800138000*/

                public boolean isSelected;
            }
        }
    }

}