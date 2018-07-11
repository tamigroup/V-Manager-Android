package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;
import java.io.Serializable;

/**
 * Created by why on 2018/7/10.
 */
public class SystemRoleListRequest extends MobileMessage implements Serializable {
    private static final long serialVersionUID = 5086330679854999508L;

    public SystemRoleListRequest(){
        super();
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.GET_SYSTEM_ROLE_LIST;
    }

    @Override
    public Class getResponseClass() {
        return SystemRoleListResponse.class;
    }
}
