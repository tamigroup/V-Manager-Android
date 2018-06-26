package com.tami.vmanager.http;

/**
 * 服务器网络访问自定义异常
 */
public class AccessException extends Exception {

    int code;

    public AccessException(int code, String message) {
        super(message);
        this.code = code;
    }
}
