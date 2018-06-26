package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 获取用户信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetAppUserResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String createOn;
	private String depId;
	private String fromPlat;
	private String iconUrl;
	private String isAdmin;
	private String mobile;
	private String nickName;
	private String password;
	private String positionName;
	private String postionId;
	private String realName;
	private String registrationId;
	private String roleId;
	private String status;
	private String systemId;
	private String systemName;
	private String updateOn;

	public GetAppUserResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public String getCreateOn() {
		return createOn;
	}

	public void setCreateOn(String createOn) {
		this.createOn = createOn;
	}

	/**
	 * @return 
	 */
	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	/**
	 * @return 
	 */
	public String getFromPlat() {
		return fromPlat;
	}

	public void setFromPlat(String fromPlat) {
		this.fromPlat = fromPlat;
	}

	/**
	 * @return 
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * @return 
	 */
	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	/**
	 * @return 
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return 
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return 
	 */
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return 
	 */
	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return 
	 */
	public String getPostionId() {
		return postionId;
	}

	public void setPostionId(String postionId) {
		this.postionId = postionId;
	}

	/**
	 * @return 
	 */
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @return 
	 */
	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	/**
	 * @return 
	 */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return 
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return 
	 */
	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	/**
	 * @return 
	 */
	public String getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(String updateOn) {
		this.updateOn = updateOn;
	}
}