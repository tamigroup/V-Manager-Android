package com.tami.vmanager.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 创建公告.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class CreateNoticeResponse extends MobileMessage implements Serializable {

    private static final long serialVersionUID = 4475033999128307977L;

    public CreateNoticeResponse() {
        super();
    }

    public boolean data;
}