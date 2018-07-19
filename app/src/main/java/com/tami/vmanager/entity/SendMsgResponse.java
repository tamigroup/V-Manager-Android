package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;


/**
 * 发送群消息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class SendMsgResponse extends MobileMessage implements Serializable{

	private static final long serialVersionUID = 6926301454994286610L;
	/**
	 * data : true
	 */

	private boolean data;


	public SendMsgResponse() {
		super();
	}


	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}
}