package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 根据酒店ID查销售人员列表（销售总+销售人员.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetSaleUserByRoleRequest implements Serializable{


	private static final long serialVersionUID = 1149691818069338701L;
	private String systemId;

	public GetSaleUserByRoleRequest() {
		super();
	}	

	/**
	 * @return 酒店ID
	 */
	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}


}