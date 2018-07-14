package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;


/**
 * 用户登录.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class LoginResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LoginResponse() {
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
    public static class Item {
        @JsonInclude(Include.NON_NULL)
        public static class UserRole {

            public	int	id;	/*1*/
            public	int	status;	/*0*/
            public	int	userId;	/*1*/
            public	String	createOn;	/*2018-05-22 08:17:24*/
            public	String	updateOn;	/*2018-05-22 08:17:22*/
            public	int	roleId;	/*2*/
        }

        private	String	registrationId;	/**/
        private	int	status;	/*0*/
        private	String	iconUrl;	/*http://vgj.oss-cn-beijing.aliyuncs.com/app/af20180703181234_43342.jpg*/
        private	String	systemName;	/*塔米智能大酒店中关村店*/
        private	String	depName;	/**/
        private	int	depId;	/*6*/
        private	String	password;	/*3f57efeb8c91c6bae91ccd6e8790f8ad*/
        private	int	fromPlat;	/*会议来源 1-V智会 2-手机APP*/
        private	int	id;	/*33*/
        private	String	nickName;	/*李双希*/
        private	String	token;	/*74382bda6c8b48e3b56fbb5f2c91bc0f*/
        private	int	positionId;	/*6*/
        private	String	realName;	/*李双希*/
        private	int	isAdmin;	/*0*/
        private	int	systemId;	/*4*/
        private	String	positionName;	/*研发部*/
        private	Long	createOn;	/*1529907675000*/
        private	String	mobile;	/*15901125418*/
        private	int	roleId;	/*0*/
        private	Long	updateOn;	/*1530612754000*/
        private List<UserRole> userRoleList;

        public String getRegistrationId() {
            return registrationId;
        }

        public void setRegistrationId(String registrationId) {
            this.registrationId = registrationId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        public int getDepId() {
            return depId;
        }

        public void setDepId(int depId) {
            this.depId = depId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getFromPlat() {
            return fromPlat;
        }

        public void setFromPlat(int fromPlat) {
            this.fromPlat = fromPlat;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getPositionId() {
            return positionId;
        }

        public void setPositionId(int positionId) {
            this.positionId = positionId;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getSystemId() {
            return systemId;
        }

        public void setSystemId(int systemId) {
            this.systemId = systemId;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public Long getCreateOn() {
            return createOn;
        }

        public void setCreateOn(Long createOn) {
            this.createOn = createOn;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public Long getUpdateOn() {
            return updateOn;
        }

        public void setUpdateOn(Long updateOn) {
            this.updateOn = updateOn;
        }

        /**
         * @return
         */
        public List<UserRole> getUserRoleList() {
            return userRoleList;
        }

        public void setUserRoleList(List<UserRole> userRoleList) {
            this.userRoleList = userRoleList;
        }
    }
}