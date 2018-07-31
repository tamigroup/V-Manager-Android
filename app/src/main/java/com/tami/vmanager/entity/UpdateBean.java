package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/19
 */
public class UpdateBean extends MobileMessage implements Serializable{


    /**
     * data : {"appId":1,"updateUrl":"update url android","mustUpdate":0,"createOn":1526640478000,"id":1,"title":"默认Android手机起始版本","updateOn":1526640483000,"versionCodeNum":101,"content":"ddsdsdsdsds","versionCode":"1.0.1"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * appId : 1
         * updateUrl : update url android
         * mustUpdate : 0
         * createOn : 1526640478000
         * id : 1
         * title : 默认Android手机起始版本
         * updateOn : 1526640483000
         * versionCodeNum : 101
         * content : ddsdsdsdsds
         * versionCode : 1.0.1
         */

        private int appId;
        private String updateUrl;
        private int mustUpdate;
        private long createOn;
        private int id;
        private String title;
        private long updateOn;
        private int versionCodeNum;
        private String content;
        private String versionCode;

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public int getMustUpdate() {
            return mustUpdate;
        }

        public void setMustUpdate(int mustUpdate) {
            this.mustUpdate = mustUpdate;
        }

        public long getCreateOn() {
            return createOn;
        }

        public void setCreateOn(long createOn) {
            this.createOn = createOn;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public long getUpdateOn() {
            return updateOn;
        }

        public void setUpdateOn(long updateOn) {
            this.updateOn = updateOn;
        }

        public int getVersionCodeNum() {
            return versionCodeNum;
        }

        public void setVersionCodeNum(int versionCodeNum) {
            this.versionCodeNum = versionCodeNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(String versionCode) {
            this.versionCode = versionCode;
        }
    }
}
