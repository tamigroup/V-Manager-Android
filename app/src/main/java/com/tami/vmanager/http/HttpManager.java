package com.tami.vmanager.http;

import com.google.gson.Gson;
import com.tami.vmanager.BuildConfig;
import com.tami.vmanager.utils.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostStringBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Created by why on 2018/6/23.
 */
public class HttpManager {

    /**
     * 返回服务器全路径URL
     *
     * @param url
     * @return
     */
    private static String getServiceUrl(String url) {
        StringBuilder sb = new StringBuilder(BuildConfig.API_SERVER_URL);
        sb.append(url);
        Logger.d("Request->URL:" + sb.toString());
        return sb.toString();
    }

    private static Map<String, String> addHeaders() {
        //头信息
        Map<String, String> map = new HashMap<>();
        return map;
    }

    /**
     * MAP转JSON字符串
     *
     * @param map
     * @return
     */
    private static String mapToJson(Map<String, String> map) {
        //可追加全局参数
        String jsonStr = new Gson().toJson(map);
        Logger.d("mapToJson -> jsonStr:" + jsonStr);
        return jsonStr;
    }

    /**
     * 服务器请求传送数据格式及编码方式
     *
     * @return
     */
    private static MediaType getMediaType() {
        return MediaType.parse("application/json; charset=utf-8");
    }

    /**
     * 添加参数
     *
     * @param url
     * @param map
     * @return
     */
    public static PostStringBuilder initPostString(String url, Map<String, String> map) {
        return OkHttpUtils.postString()
                .url(getServiceUrl(url))
                .content(mapToJson(map))
                .mediaType(getMediaType())
                .headers(addHeaders());
    }

}
