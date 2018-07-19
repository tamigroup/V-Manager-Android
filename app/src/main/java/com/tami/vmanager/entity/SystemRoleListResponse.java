package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * Created by why on 2018/7/10.
 */

@JsonInclude(Include.NON_NULL)
public class SystemRoleListResponse extends MobileMessage implements Serializable {
    public static final long serialVersionUID = -1411520189206621082L;

    public SystemRoleListResponse() {
        super();
    }

    public List<Item> data;

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable{

        private static final long serialVersionUID = 7467530105971002997L;
        public Integer id;	/*1*/
        public Integer status;	/*0*/
        public String mark;	/*监看本酒店所有会议数据*/
        public String allowNum;	/*若干*/
        public String roleName;	/*高管*/
        public Integer systemId;	/*0*/
        public Long createOn;	/*1526892943000*/
        public Long updateOn;	/*1526892946000*/
    }
}
