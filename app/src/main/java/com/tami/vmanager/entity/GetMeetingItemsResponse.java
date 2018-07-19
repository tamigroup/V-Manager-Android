package com.tami.vmanager.entity;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tami.vmanager.utils.TimeUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 查询系统内置的会议流程所有节点.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingItemsResponse extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -6117770465493762199L;

    public GetMeetingItemsResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable{

        private static final long serialVersionUID = 632041108744624928L;
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Comparable<Item> ,Serializable{
            private static final long serialVersionUID = 4650519290177901740L;
            public int id;	/*1*/
            public int selStatus;	/*0*/
            public int parentId;	/*0*/
            public int orderBy;	/*0*/
            public int status;	/*0*/
            public String name;	/*会场就绪*/
            public long createOn;	/*1526972249000*/
            public long updateOn;	/*1526972253000*/
            public int roleId;	/*3*/
            public int pid;	/*1*/
            public int defaultMinutes;	/*0*/
            public long startOn;	/**/
            public int meetingId;	/*0*/
            public int systemId;	/*0*/

            public boolean isCustom;
            public boolean isSelected;

            @Override
            public int compareTo(@NonNull Item item) {
                if (startOn == 0) {
                    return 1;
                }
                if (item.startOn == 0) {
                    return -1;
                }
                String thisOrderTime = TimeUtils.date2String(new Date(startOn), TimeUtils.DATE_HHMM_SLASH);
                String orderTime = TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH);
                return thisOrderTime.compareTo(orderTime);
            }

            public Role role;

            @JsonInclude(Include.NON_NULL)
            public static class Role implements Serializable{
                private static final long serialVersionUID = 5800393170967688385L;
                public int id;
                public String name;
            }
        }
    }
}