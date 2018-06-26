package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


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
    public class Item {
        @JsonInclude(Include.NON_NULL)
        public class ElementUserRoleList {

            private String userId;

            /**
             * @return 用户ID
             */
            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }

        private String createOn;
        private String depId;
        private String depName;
        private String fromPlat;
        private String iconUrl;
        private String id;
        private String isAdmin;
        private String mobile;
        private String nickName;
        private String password;
        private String positionId;
        private String positionName;
        private String realName;
        private String roleId;
        private String status;
        private String systemId;
        private String systemName;
        private String token;
        private String updateOn;
        private List<ElementUserRoleList> userRoleList;


        /**
         * @return 创建时间
         */
        public String getCreateOn() {
            return createOn;
        }

        public void setCreateOn(String createOn) {
            this.createOn = createOn;
        }

        /**
         * @return 所属部门ID
         */
        public String getDepId() {
            return depId;
        }

        public void setDepId(String depId) {
            this.depId = depId;
        }

        public String getDepName() {
            return depName;
        }

        public void setDepName(String depName) {
            this.depName = depName;
        }

        /**
         * @return 来自于平台ID
         */
        public String getFromPlat() {
            return fromPlat;
        }

        public void setFromPlat(String fromPlat) {
            this.fromPlat = fromPlat;
        }

        /**
         * @return 用户Id
         */
        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        /**
         * @return 创建时间
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return 是否管理员用户
         */
        public String getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(String isAdmin) {
            this.isAdmin = isAdmin;
        }

        /**
         * @return 登陆手机号
         */
        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        /**
         * @return 昵称
         */
        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        /**
         * @return 密码
         */
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @return 职位ID
         */
        public String getPositionId() {
            return positionId;
        }

        public void setPositionId(String positionId) {
            this.positionId = positionId;
        }

        /**
         * @return 职位名称
         */
        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        /**
         * @return 真实姓名
         */
        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        /**
         * @return 用户角色ID
         */
        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        /**
         * @return 用户状态     0-->正常  1-->停用   -1-->删除
         */
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return 所属机构ID
         */
        public String getSystemId() {
            return systemId;
        }

        public void setSystemId(String systemId) {
            this.systemId = systemId;
        }

        /**
         * @return 用户所属机构
         */
        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        /**
         * @return token
         */
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        /**
         * @return 更新时间
         */
        public String getUpdateOn() {
            return updateOn;
        }

        public void setUpdateOn(String updateOn) {
            this.updateOn = updateOn;
        }

        /**
         * @return
         */
        public List<ElementUserRoleList> getUserRoleList() {
            return userRoleList;
        }

        public void setUserRoleList(List<ElementUserRoleList> userRoleList) {
            this.userRoleList = userRoleList;
        }
    }
}