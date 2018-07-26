package com.tami.vmanager.message;

/**
 * Created by why on 2018/7/25.
 */
public class MessageEvent {

    private String message;
    private boolean isRefresh;

    public MessageEvent(boolean isRefresh) {
        this.isRefresh = isRefresh;
    }

    public MessageEvent(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
