package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 创建公告.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class CreateNoticeRequest extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 6180099630573853500L;

    public CreateNoticeRequest() {
        super();
    }

    private String content;
    private int groupType;
    private int isTop;
    private int meetingId;
    private int noticeType;
    private int systemId;
    private String title;
    private int userId;


    /**
     * @return 公告内容
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return 群组类型  1-V管家 2-V智会 3-VV群
     */
    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    /**
     * @return 是否置顶
     */
    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    /**
     * @return 会议ID
     */
    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    /**
     * @return 会议类型   1-系统通知  2-会务广播 3-群内公告
     */
    public int getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(int noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * @return 酒店ID
     */
    public int getSystemId() {
        return systemId;
    }

    public void setSystemId(int systemId) {
        this.systemId = systemId;
    }

    /**
     * @return 公告标题
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 用户ID
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @Override
    public Class getResponseClass() {
        return CreateNoticeResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.CREATE_NOTICE;
    }
}