package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

/**
 * Created by why on 2018/7/14.
 */
@JsonInclude(Include.NON_NULL)
public class GetSelectMeetingItemsUserResponse extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -714898414343136695L;

    public GetSelectMeetingItemsUserResponse() {
        super();
    }


}
