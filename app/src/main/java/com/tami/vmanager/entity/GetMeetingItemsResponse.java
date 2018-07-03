package com.tami.vmanager.entity;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
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
            public Long createOn;	/*1526972249000*/
            public Long updateOn;	/*1526972253000*/
            public int roleId;	/*3*/

            @Override
            public int compareTo(@NonNull Item o) {
                return this.createOn.compareTo(o.createOn);
            }
        }
    }
}