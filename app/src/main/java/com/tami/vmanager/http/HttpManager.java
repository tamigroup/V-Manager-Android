package com.tami.vmanager.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
@Deprecated
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


    /**
     * 判断是否有网
     *
     * @param context
     * @return
     */
    public static int getType(Context context) {
        int mState = -1;//-1代表无网络
        //获取系统提供的服务，转换成连接管理类，这个类专门处理连接相关的东西
        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        //NetworkInfo封装了网络连接的信息
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        //如果网络连接的信息等于空的话，代表无网络
        if (activeNetworkInfo == null) {
            return mState;
        }
        int type = activeNetworkInfo.getType();
        if (type == systemService.TYPE_WIFI) {//代表现在是wifi网络
            mState = 1;
        } else if (type == systemService.TYPE_MOBILE) {
            mState = 0;//代表现在是蜂窝网络
        }
        return mState;
    }
}
