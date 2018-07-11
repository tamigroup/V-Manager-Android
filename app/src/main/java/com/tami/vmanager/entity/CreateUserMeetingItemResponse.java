package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

/**
 * Created by why on 2018/7/10.
 */
@JsonInclude(Include.NON_NULL)
public class CreateUserMeetingItemResponse extends MobileMessage implements Serializable {

    public static final long serialVersionUID = 7921725888049402564L;

    public CreateUserMeetingItemResponse() {
        super();
    }

    public Item data;

    @JsonInclude(Include.NON_NULL)
    public static class Item {
        public int selStatus;	/*0*/
        public int orderBy;	/*0*/
        public int status;	/*0*/
        public int pid;	/*20*/
        public int defaultMinutes;	/*0*/
        public int id;	/*21*/
        public String startOn;	/**/
        public String name;	/*好纠结*/
        public int meetingId;	/*1*/
        public int systemId;	/*4*/
        public Long createOn;	/*1531218470208*/
        public int roleId;	/*1*/
        public Long updateOn;	/*1531218470208*/
    }
}
