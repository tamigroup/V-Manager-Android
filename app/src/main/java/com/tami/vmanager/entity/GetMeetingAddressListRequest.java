package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;


/**
 * 根据酒店ID查询会议厅List.客户端请求
 *
 * @author 代码生成器v1.0
 */
public class GetMeetingAddressListRequest extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String systemId;

    public GetMeetingAddressListRequest() {
        super();
    }

    /**
     * @return 酒店ID
     */
    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public Class getResponseClass() {
        return GetMeetingAddressListResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_MEETING_ADDRESS_LIST;
    }
}