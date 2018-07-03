package com.tami.vmanager.entity;

import com.tami.vmanager.http.HttpKey;

import java.io.Serializable;

/**
 * Created by why on 2018/7/3.
 */
public class UploadImageRequest extends FileMobileMessage implements Serializable {


    private static final long serialVersionUID = -9116566936669695638L;

    @Override
    public Class getResponseClass() {
        return UploadImageResponse.class;
    }

    @Override
    public String getRequestUrl() {
        return HttpKey.UPLOAD_IMAGE;
    }
}
