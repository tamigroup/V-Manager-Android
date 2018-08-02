package com.tami.vmanager.event;

/**
 * Created by Tang on 2018/8/2
 * EventBus 事件管理器
 */
public class EventManage {
    /**
     * 极光 群消息 事件
     */
    public static String JPUSH_GROUPMESSAGEEVENT = "have_groupMessage";
    public static class Jpush_GroupMessageEvent{
        private String message;
        public  Jpush_GroupMessageEvent(String message){
            this.message=message;
        }
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
