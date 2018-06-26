package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 根据酒店ID查询会议厅List.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class GetMeetingAddressListRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String systemId;

	public GetMeetingAddressListRequest() {
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