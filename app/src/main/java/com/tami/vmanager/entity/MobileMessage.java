package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by why on 2018/6/25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileMessage {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Class getResponseClass() {
        return null;
    }

    public String getRequsetUrl() {
        return null;
    }
}
