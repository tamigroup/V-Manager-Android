package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

/**
 * Created by why on 2018/7/13.
 */
@JsonInclude(Include.NON_NULL)
public class ModifyMeetingResponse extends MobileMessage implements Serializable {
    private static final long serialVersionUID = 4414289572677642677L;

    public ModifyMeetingResponse() {
        super();
    }

    public boolean data;
}
