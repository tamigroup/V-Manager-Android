package com.tami.vmanager.entity;

import android.support.annotation.NonNull;

import java.io.Serializable;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 查询系统内置的会议流程所有节点.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingItemsResponse extends MobileMessage implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    /**
     * @see com.bwapi.TM.message.entity.GetMeetingItemsResponse#getDataList
     */
    @JsonInclude(Include.NON_NULL)
    public static class ElementDataList implements Comparable<ElementDataList> {

        private String createOn;
        private String id;
        private String name;
        private String roleId;
        private String selStatus;
        private String updateOn;

        /**
         * @return
         */
        public String getCreateOn() {
            return createOn;
        }

        public void setCreateOn(String createOn) {
            this.createOn = createOn;
        }

        /**
         * @return
         */
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return
         */
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return
         */
        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        /**
         * @return
         */
        public String getSelStatus() {
            return selStatus;
        }

        public void setSelStatus(String selStatus) {
            this.selStatus = selStatus;
        }

        /**
         * @return
         */
        public String getUpdateOn() {
            return updateOn;
        }

        public void setUpdateOn(String updateOn) {
            this.updateOn = updateOn;
        }

        @Override
        public int compareTo(@NonNull ElementDataList item) {
            if (item.createOn.compareToIgnoreCase(this.createOn) > 0) {
                return -1;
            }
            return 1;
        }
    }

    private List<ElementDataList> dataList;

    public GetMeetingItemsResponse() {
        super();
    }


    /**
     * @return
     */
    public List<ElementDataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<ElementDataList> dataList) {
        this.dataList = dataList;
    }
}