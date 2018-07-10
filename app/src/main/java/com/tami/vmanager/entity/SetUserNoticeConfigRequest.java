package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 保存消息设置.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class SetUserNoticeConfigRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SetUserNoticeConfigRequest() {
        super();
    }

    private int groupChatNotice;
    private int hostNotice;
    private int meetingNotice;
    private int satisfactionNotice;
    private int systemNotice;
    private int userId;
    private int taskDistribution;

    public int getGroupChatNotice() {
        return groupChatNotice;
    }

    public void setGroupChatNotice(int groupChatNotice) {
        this.groupChatNotice = groupChatNotice;
    }

    public int getHostNotice() {
        return hostNotice;
    }

    public void setHostNotice(int hostNotice) {
        this.hostNotice = hostNotice;
    }

    public int getMeetingNotice() {
        return meetingNotice;
    }

    public void setMeetingNotice(int meetingNotice) {
        this.meetingNotice = meetingNotice;
    }

    public int getSatisfactionNotice() {
        return satisfactionNotice;
    }

    public void setSatisfactionNotice(int satisfactionNotice) {
        this.satisfactionNotice = satisfactionNotice;
    }

    public int getSystemNotice() {
        return systemNotice;
    }

    public void setSystemNotice(int systemNotice) {
        this.systemNotice = systemNotice;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskDistribution() {
        return taskDistribution;
    }

    public void setTaskDistribution(int taskDistribution) {
        this.taskDistribution = taskDistribution;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.USER_NOTICE_CONFIG;
    }

    @Override
    public Class getResponseClass() {
        return SetUserNoticeConfigResponse.class;
    }
}