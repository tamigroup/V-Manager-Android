package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * 用户权限
 * Created by why on 2018/6/25.
 */
public class UserRoleEntity implements Serializable{

    private static final long serialVersionUID = -3284347562263555503L;
    public int id;
    public int status;
    public int userId;
    public String createOn;
    public String updateOn;
    public int roleId;
}
