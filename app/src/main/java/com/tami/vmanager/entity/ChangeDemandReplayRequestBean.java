package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/10
 * 需求变化 活动变化 回复
 */
public class ChangeDemandReplayRequestBean extends MobileMessage implements Serializable{

    /**
     * meetingRequirementId : 4
     * replyUserId : 1
     * replyContent : this is log
     */

    private int meetingRequirementId;
    private int replyUserId;
    private String replyContent;

    public int getMeetingRequirementId() {
        return meetingRequirementId;
    }

    public void setMeetingRequirementId(int meetingRequirementId) {
        this.meetingRequirementId = meetingRequirementId;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.REPLY_MEETING_REQUIREMENT;
    }

    @Override
    public Class getResponseClass() {
        return ChangeDemandReplayResponseBean.class;
    }
}
