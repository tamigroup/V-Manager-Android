package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by why on 2018/7/3.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileMobileMessage {

    public String[] filePath;

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

    public String getRequestUrl() {
        return null;
    }
}
