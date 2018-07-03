package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * Created by why on 2018/7/3.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadImageResponse extends FileMobileMessage implements Serializable {

    private static final long serialVersionUID = -7574894694493932965L;

    public UploadImageResponse() {
        super();
    }

    public Item data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Item {
        public List<String> dataList;
    }
}
