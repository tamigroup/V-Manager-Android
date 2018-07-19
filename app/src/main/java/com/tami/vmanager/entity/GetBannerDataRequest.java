package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 获取(合同金额/已举办/待举办).客户端请求
 *
 * @author 代码生成器v1.0
 */
public class GetBannerDataRequest extends MobileMessage implements Serializable {


    private static final long serialVersionUID = -6486514127119525735L;
    private String type;
    private String userId;

    public GetBannerDataRequest() {
        super();
    }

    /**
     * @return 类别  参数固定为0，1，2  【 0-今天  1-本月  2-本年】
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public Class getResponseClass() {
        return GetBannerDataResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.USER_GET_BANNER_DATA;
    }
}