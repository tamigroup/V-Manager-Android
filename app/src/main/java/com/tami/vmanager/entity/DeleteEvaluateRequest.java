package com.tami.vmanager.entity;

import java.io.Serializable;


/**
 * 删除评价.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class DeleteEvaluateRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String id;

	public DeleteEvaluateRequest() {
		super();
	}	

	/**
	 * @return 评论ID
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}