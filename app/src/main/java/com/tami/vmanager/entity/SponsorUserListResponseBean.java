package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/14
 */
public class SponsorUserListResponseBean extends MobileMessage implements Serializable {

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

    public static class DataBean {
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
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
