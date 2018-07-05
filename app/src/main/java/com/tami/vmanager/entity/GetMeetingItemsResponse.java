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
    public static final long serialVersionUID = 1L;

    public GetMeetingItemsResponse() {
        super();
    }

    public Array data;

    public static class Array {

        public List<Item> dataList;

        public static class Item implements Comparable<Item> {
            public int id;	/*1*/
            public int selStatus;	/*0*/
            public int parentId;	/*0*/
            public int orderBy;	/*0*/
            public int status;	/*0*/
            public String name;	/*会场就绪*/
            public long createOn;	/*1526972249000*/
            public long updateOn;	/*1526972253000*/
            public int roleId;	/*3*/

            public boolean isCustom;
            public boolean isSelected;

            @Override
            public int compareTo(@NonNull Item item) {
                if (createOn == 0) {
                    return -1;
                }
                if (item.createOn == 0) {
                    return 1;
                }
                String thisOrderTime = TimeUtils.date2String(new Date(createOn), TimeUtils.DATE_HHMM_SLASH);
                String orderTime = TimeUtils.date2String(new Date(item.createOn), TimeUtils.DATE_HHMM_SLASH);
                return thisOrderTime.compareTo(orderTime);
            }
        }
    }
}