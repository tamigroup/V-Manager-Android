package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 发送群消息.客户端请求
 * 
 * @author 代码生成器v1.0
 * 
 */
public class SendMsgRequest extends MobileMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String content;
	private String meetingId;
	private String type;
	private String userIcon;
	private String userId;
	private String userName;

	public SendMsgRequest() {
		super();
	}	

	/**
	 * @return 消息内容
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	 * @return 类别  1-v管家  2-v智会 3-VV群
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return 用户头像(当群消息来自v智会  或者 来自vv群时需传userIcon)
	 */
	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
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

	/**
	 * @return 用户姓名(当群消息来自v智会  或者 来自vv群时需传userName)
	 */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public Class getResponseClass() {
		return SendMsgResponse.class;
	}

	@Override
	public String getRequestUrl() {
		return HttpKey.SEND_CHAT_MSG;
	}
}