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


    private String groupChatNotice;
    private String hostNotice;
    private String meetingNotice;
    private String satisfactionNotice;
    private String systemNotice;
    private String userId;

    public SetUserNoticeConfigRequest() {
        super();
    }

    /**
     * @return 群聊通知
     */
    public String getGroupChatNotice() {
        return groupChatNotice;
    }

    public void setGroupChatNotice(String groupChatNotice) {
        this.groupChatNotice = groupChatNotice;
    }

    /**
     * @return 主办方消息
     */
    public String getHostNotice() {
        return hostNotice;
    }

    public void setHostNotice(String hostNotice) {
        this.hostNotice = hostNotice;
    }

    /**
     * @return 会务广播
     */
    public String getMeetingNotice() {
        return meetingNotice;
    }

    public void setMeetingNotice(String meetingNotice) {
        this.meetingNotice = meetingNotice;
    }

    /**
     * @return 满意度通知
     */
    public String getSatisfactionNotice() {
        return satisfactionNotice;
    }

    public void setSatisfactionNotice(String satisfactionNotice) {
        this.satisfactionNotice = satisfactionNotice;
    }

    /**
     * @return 系统消息
     */
    public String getSystemNotice() {
        return systemNotice;
    }

    public void setSystemNotice(String systemNotice) {
        this.systemNotice = systemNotice;
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

    @Override
    public String getRequestUrl() {
        return HttpKey.USER_NOTICE_CONFIG;
    }

    @Override
    public Class getResponseClass() {
        return SetUserNoticeConfigResponse.class;
    }
}