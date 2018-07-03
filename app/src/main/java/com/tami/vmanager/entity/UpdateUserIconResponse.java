package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 修改用户头像.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class UpdateUserIconResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	public static final long serialVersionUID = 1L;



	public UpdateUserIconResponse() {
		super();
	}

	public Item data;

	@JsonInclude(Include.NON_NULL)
	public static class Item{

		public	String	registrationId;	/*String*/
		public	int	status;	/*0*/
		public	String	systemName;	/*String*/
		public	String	iconUrl;	/*https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg*/
		public	String	password;	/*957fd5cfba6e9b65945eb898fec97116*/
		public	int	depId;	/*2*/
		public	int	fromPlat;	/*1*/
		public	int	id;	/*30*/
		public	String	token;	/*String*/
		public	String	nickName;	/*李宴会roleID-3*/
		public	int	positionId;	/*2*/
		public	String	realName;	/*大熊*/
		public	int	isAdmin;	/*0*/
		public List<ElementUserRoleList> userRoleList;	/*String*/
		public	int	systemId;	/*4*/
		public	String	positionName;	/*String*/
		public	Long	createOn;	/*1528333906000*/
		public	Long	updateOn;	/*1528936436396*/
		public	int	roleId;	/*0*/
		public	String	mobile;	/*1500000015*/


		@JsonInclude(Include.NON_NULL)
		public static class ElementUserRoleList {

			public	Integer	id;	/*1*/
			public	Integer	status;	/*0*/
			public	Integer	userId;	/*1*/
			public	String	createOn;	/*2018-05-22 08:17:24*/
			public	String	updateOn;	/*2018-05-22 08:17:22*/
			public	Integer	roleId;	/*2*/
		}
	}

}