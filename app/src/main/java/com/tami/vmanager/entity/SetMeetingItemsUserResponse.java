package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 会议流程节点分配人员.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class SetMeetingItemsUserResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = -2411581241617963843L;

    public SetMeetingItemsUserResponse() {
        super();
    }

    public boolean data;
}