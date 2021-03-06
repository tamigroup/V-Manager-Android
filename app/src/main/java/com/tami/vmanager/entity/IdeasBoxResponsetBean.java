package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Tang on 2018/7/6
 * 意见箱 服务器返回
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdeasBoxResponsetBean extends MobileMessage implements Serializable{

    private static final long serialVersionUID = 2943219640004733057L;
    /**
     * data : {"avg":4}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -6668510419303100956L;
        /**
         * avg : 4
         */

        private int avg;

        public int getAvg() {
            return avg;
        }

        public void setAvg(int avg) {
            this.avg = avg;
        }
    }
}
