package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 保存会议合同金额.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SaveMeetingMoneyRequest implements Serializable{


	private static final long serialVersionUID = -1669163360553448028L;
	private String contractMoney;
	private String meetingId;
	private String payMoney;

	public SaveMeetingMoneyRequest() {
		super();
	}	

	/**
	 * @return 合同金额
	 */
	public String getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}

	/**
	 * @return 会议ID
	 */
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	/**
	 * @return 已付金额
	 */
	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}


}