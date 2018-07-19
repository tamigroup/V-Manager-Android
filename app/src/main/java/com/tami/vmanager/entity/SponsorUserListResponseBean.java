package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/14
 */
@JsonInclude(Include.NON_NULL)
public class SponsorUserListResponseBean extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -7318807829603872728L;
    /**
     * data : {"dataList":[{"userName":"666"},{"userName":"444"},{"userName":"888"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    @JsonInclude(Include.NON_NULL)
    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 6605726649462838659L;
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }
        @JsonInclude(Include.NON_NULL)
        public static class DataListBean implements Serializable{
            private static final long serialVersionUID = 358938648908282906L;
            /**
             * userName : 666
             */

            private String userName;

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
