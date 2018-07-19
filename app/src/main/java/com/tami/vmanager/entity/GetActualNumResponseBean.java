package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/16
 * 获取会议实到人数 服务器返回
 */
public class GetActualNumResponseBean extends MobileMessage implements Serializable{

    private static final long serialVersionUID = -3547690948822334413L;
    /**
     * data : {"actualNum":10}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * actualNum : 10
         */

        private int actualNum;

        public int getActualNum() {
            return actualNum;
        }

        public void setActualNum(int actualNum) {
            this.actualNum = actualNum;
        }
    }
}
