package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;


/**
 * 获取部门用户组.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetUserDepartmentResponse extends MobileMessage implements Serializable {
    public static final long serialVersionUID = 9101313760090883038L;

    public GetUserDepartmentResponse() {
        super();
    }

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable {
        private static final long serialVersionUID = 4270970555391754050L;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable {
            private static final long serialVersionUID = 5782236287510635361L;

            @JsonInclude(Include.NON_NULL)
            public static class User implements Serializable {
                private static final long serialVersionUID = 3285339199850530462L;

                public String registrationId;	/*1104a89792f80312c8f*/
                public int status;	/*0*/
                public String iconUrl;	/**/
                public String systemName;	/**/
                public String depName;	/**/
                public int depId;	/*6*/
                public String password;	/*0a108861185063ed4a7402f18733f944*/
                public int fromPlat;	/*0*/
                public int id;	/*85*/
                public String nickName;	/**/
                public String token;	/**/
                public int positionId;	/*6*/
                public String deviceToken;	/**/
                public int isAdmin;	/*0*/
                public String realName;	/*松鼠松鼠松鼠松鼠松鼠松鼠松鼠松鼠松鼠吧吧Vv反福福福福福福发*/
                public int systemId;	/*4*/
                public String userRoleList;	/**/
                public String positionName;	/*研发部*/
                public Long createOn;	/*1530175479000*/
                public Long updateOn;	/*1533122115000*/
                public int roleId;	/*0*/
                public String mobile;	/*13000000099*/
            }

            public List<User> userList;	/*List<TUserList>*/

            public int id;	/*6*/
            public int person_num;	/*0*/
            public int orderBy;	/*0*/
            public int status;	/*0*/
            public String positionList;	/**/
            public String depName;	/*我是秘书长*/
            public int systemId;	/*4*/
            public int position_num;	/*0*/
            public Long createOn;	/*1528020585000*/
            public Long updateOn;	/*1530266283000*/


        }

        public List<Item> dataList;	/*List<TDataList>*/

    }

    public Array data;	/*TData*/
}