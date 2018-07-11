package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 添加（ 编辑）会议流程节点.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class SetMeetingItemsResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SetMeetingItemsResponse() {
        super();
    }

    public boolean data;
}