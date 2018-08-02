package com.tami.vmanager.jpush;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/31
 */
public class ExtraBean implements Serializable {


    /**
     * msgExtrasKey : needChange,208
     */

    private String msgExtrasKey;

    public String getMsgExtrasKey() {
        return msgExtrasKey;
    }

    public void setMsgExtrasKey(String msgExtrasKey) {
        this.msgExtrasKey = msgExtrasKey;
    }
}
