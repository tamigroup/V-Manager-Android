package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询会议统计信息.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingStatisticsResponse extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String actualNum;
	private String evaLuateNum;
	private String payPercent;

	public GetMeetingStatisticsResponse() {
		super();
	}

	


	/**
	 * @return 
	 */
	public String getActualNum() {
		return actualNum;
	}

	public void setActualNum(String actualNum) {
		this.actualNum = actualNum;
	}

	/**
	 * @return 
	 */
	public String getEvaLuateNum() {
		return evaLuateNum;
	}

	public void setEvaLuateNum(String evaLuateNum) {
		this.evaLuateNum = evaLuateNum;
	}

	/**
	 * @return 
	 */
	public String getPayPercent() {
		return payPercent;
	}

	public void setPayPercent(String payPercent) {
		this.payPercent = payPercent;
	}
}