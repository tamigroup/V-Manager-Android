package com.tami.vmanager.entity;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 手机号和验证码登陆.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
@Deprecated
public class LoginSmsResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see com.bwapi.TM.message.entity.LoginSmsResponse#getUserRoleList
	 * 
	 */
	@JsonInclude(Include.NON_NULL)
	public static class ElementUserRoleList {

		private String id;
		private String userId;

		/**
		 * @return 角色ID
		 */
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

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
	private String fromPlat;
	private String isAdmin;
	private String positionName;
	private String roleId;
	private String status;
	private String systemName;
	private String token;
	private String updateOn;
	private List<ElementUserRoleList> userRoleList;

	public LoginSmsResponse() {
		super();
	}

	


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
	 * @return 来自于平台ID
	 */
	public String getFromPlat() {
		return fromPlat;
	}

	public void setFromPlat(String fromPlat) {
		this.fromPlat = fromPlat;
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
	 * @return 职位名称
	 */
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
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