package com.tami.vmanager.entity;

import com.tami.vmanager.base.BaseEntity;

import java.io.Serializable;

/**
 * Created by why on 2018/6/20.
 */
public class LoginEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3476241213064991015L;

    public Item data;

    public class Item {
        public Integer userId;
        public String headImgUrl;
        public String nickName;
        public String eMail;
    }
}
