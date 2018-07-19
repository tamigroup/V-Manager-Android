package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 保存添加参会人数.服务端响应
 * 
 * @author 代码生成器v1.0
 * 
 */
@JsonInclude(Include.NON_NULL)
public class SaveGuestNumResponse extends MobileMessage implements Serializable{


	private static final long serialVersionUID = 2294406507256065128L;

	public SaveGuestNumResponse() {
		super();
	}

	

}