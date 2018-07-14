package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tang on 2018/7/13
 * 人员名单 服务端
 */
public class PersonnelListResponseBean extends MobileMessage implements Serializable {


    /**
     * data : {"dataList":[{"name":"张涛_1","type":0,"meetingId":0},{"name":"张涛_2","type":1,"meetingId":0},{"name":"张涛_3","type":0,"meetingId":0},{"name":"张涛_4","type":1,"meetingId":0},{"name":"张涛_5","type":0,"meetingId":0},{"name":"张涛_6","type":1,"meetingId":0},{"name":"张涛_7","type":0,"meetingId":0},{"name":"张涛_8","type":1,"meetingId":0},{"name":"张涛_9","type":0,"meetingId":0},{"name":"张涛_10","type":1,"meetingId":0}]}
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

        public static class DataListBean extends IndexNameBean{
            /**
             * name : 张涛_1
             * type : 0
             * meetingId : 0
             */

            private String name;
            private int type;
            private int meetingId;

            public String getName() {
                return name;
            }

            public DataListBean setName(String name) {
                this.name = name;
                return this;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getMeetingId() {
                return meetingId;
            }

            public void setMeetingId(int meetingId) {
                this.meetingId = meetingId;
            }

            @Override
            public String getTarget() {
                return name;
            }
        }
    }
}
