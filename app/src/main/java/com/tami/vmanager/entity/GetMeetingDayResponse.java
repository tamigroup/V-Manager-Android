package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;

/**
 * Created by why on 2018/7/10.
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingDayResponse extends MobileMessage implements Serializable {
    private static final long serialVersionUID = 2686076847117790285L;

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array {
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item {
            public String day;
        }
    }
}
