package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 创建会议.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class CreateMeetingResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 3355150388976258699L;

    public CreateMeetingResponse() {
        super();
    }


    public Item data;

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable{

        private static final long serialVersionUID = 3726954755888507726L;

        public int meetingId;
    }
}