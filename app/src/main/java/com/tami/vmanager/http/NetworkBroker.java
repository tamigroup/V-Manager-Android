package com.tami.vmanager.http;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tami.vmanager.activity.LoginActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.MobileMessage;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.NetworkUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;

/**
 * 网络访问代理
 */
public class NetworkBroker extends BaseBroker {

    private String CancelTag = NetworkBroker.class.getSimpleName();
    //正式环境
    public static final String BASE_URI = "http://192.168.103.104:8080/apis/tm/";

    public static final int NETWORK_UNAVAILABLE = -10000;
    public static final int ACCESS_TIME_OUT = -10001;
    public static final int SUCCESS = 0;
    public static final int UNKNOWN_ERR = -1;
    public static final int SERVER_TIME_OUT = -100;
    public static final int NO_AUTH = -101;
    public static final int CONCURRENT = -102;
    public static final int AUTH_FAIL = -103;
    public static final int VFCODE_WRONG = -1002;
    public static final int VFCODE_BLANK = -1003;

    protected BehaviorSubject<Boolean> loadingSubject = BehaviorSubject.create();
    private Dialog loadingDialog = null;
    private int loadingCount = 0;

    public NetworkBroker(Context context) {
        super(context);

        loadingSubject.observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoading -> {
                    if (isLoading) {
                        if (null == loadingDialog) {
                            loadingDialog = ProgressLoader.create(context);
                        }
                        if (0 == loadingCount) loadingDialog.show();
                        this.loadingCount++;
                    } else {
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            this.loadingCount--;
                            if (0 == loadingCount) {
                                if (null != loadingDialog) {
                                    loadingDialog.dismiss();
                                    loadingDialog = null;
                                }
                            }
                        }, 250);
                    }
                });
    }

    public static interface Callback {
        public void apply(Exception ex, MobileMessage response);
    }

    /**
     * 网络访问
     *
     * @param request  请求报文
     * @param callback 回调接口
     */
    public void ask(final MobileMessage request, final Callback callback) {
        this.ask(request, true, callback);
    }

    public void setCancelTag(String cancelTag) {
        this.CancelTag = cancelTag;
    }

    /**
     * 网络访问
     * 增加网络连接判断
     *
     * @param request  请求报文
     * @param callback 回调接口
     */
    public void ask(final MobileMessage request, final boolean showLoading, final Callback callback) {
        final ObjectMapper mapper = new ObjectMapper();
        // 忽略 不需要的字段 add by chentong
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = null;
        try {
            json = mapper.writeValueAsString(request);
        } catch (Exception ex0) {
            callback.apply(ex0, null);
            return;
        }
        // 网络未连接  chentong
        if (!NetworkUtil.isConnectingToInternet(this.context)) {
            callback.apply(new AccessException(-1, "未能连上网络"), null);
            return;
        }
        Logger.d("发送报文:" + CancelTag + json);
        String uri = BASE_URI + request.getRequsetUrl();
        if (showLoading) this.loadingSubject.onNext(true);
        String token = null;
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            token = item.getToken();
        }
        PostStringBuilder postStringBuilder = OkHttpUtils.postString()
                .mediaType(MediaType.parse("application/json;charset=UTF-8"))
                .url(uri)
                .content(json);
        if (token != null) {
            postStringBuilder.addHeader("token", token);
        }
        postStringBuilder.tag(CancelTag)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception ex1, int id) {
                        if (showLoading) loadingSubject.onNext(false);
                        Logger.d("接受报文: 错误" + CancelTag + ex1.getLocalizedMessage());
                        callback.apply(ex1, null);
                        return;
                    }

                    @Override
                    public void onResponse(String responseJson, int id) {
                        if (showLoading) loadingSubject.onNext(false);
                        Logger.d("接受报文:" + CancelTag + responseJson);
                        Logger.d("getResponseClass:"  + request.getResponseClass());
                        try {
                            MobileMessage response = (MobileMessage) mapper.readValue(responseJson, request.getResponseClass());

                            int statusCode = response.getCode();
                            String statusMessage = response.getMessage();

                            if (statusCode == -1) {
                                callback.apply(new AccessException(-1, "未知错误"), null);
                            } else {
                                int code = Integer.valueOf(statusCode);
                                if (NO_AUTH == code) {
//                                    // Session过期引起的重新登录
//                                    LocalSettings.inst().clearLogin();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                } else if (UNKNOWN_ERR == code) {
                                    callback.apply(new AccessException(-1, statusMessage), null);
                                } else {
                                    callback.apply(null, response);
                                }
                            }
                        } catch (IOException ex2) {
                            callback.apply(ex2, null);
                        }
                    }
                });

    }

//    public void ask(final MobileMessage request, final boolean showLoading, final Callback callback) {
//        ObjectMapper mapper = new ObjectMapper();
//        // 忽略 不需要的字段 add by chentong
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        String json = null;
//        try {
//            json = mapper.writeValueAsString(request);
//        } catch (Exception ex0) {
//            callback.apply(ex0, null);
//            return;
//        }
//        // 网络未连接  chentong
//         if(!NetworkUtil.isConnectingToInternet(this.context)){
//             callback.apply(new AccessException(-1, "未能连上网络"), null);
//             return;
//         }
//        Logger.d("发送报文:" + json);
//        String uri = BASE_URI + request.getRequsetUrl();
//        if (showLoading) this.loadingSubject.onNext(true);
//        Ion.with(this.context)
//                .load(uri)
//                .setHeader("Content-Type", "application/json;charset=UTF-8")
//                .setStringBody(json)
//                .asString(Charset.forName("UTF-8"))
//                .setCallback((ex1, responseJson) -> {
//                    if (showLoading) this.loadingSubject.onNext(false);
//                    if (ex1 != null) {
////                        callback.apply(ex1, null);
//                        callback.apply(ex1, null);
//                        return;
//                    }
//                    Logger.d("接受报文:" + responseJson);
//                    try {
//                        MobileMessage response = (MobileMessage) mapper.readValue(responseJson, request.getResponseClass());
//
//                        String statusCode = response.getStatusCode();
//                        String statusMessage = response.getStatusMessage();
//
//                        if (null == statusCode) {
//                            callback.apply(new AccessException(-1, "未知错误"), null);
//                        } else {
//                            int code = Integer.valueOf(statusCode);
//                            if (NO_AUTH == code) {
//                                // Session过期引起的重新登录
//                                LocalSettings.inst().clearLogin();
//                                Intent intent = new Intent(this.context, MasterActivity.class);
//                                this.context.startActivity(intent);
//                            } else if (UNKNOWN_ERR == code) {
//                                callback.apply(new AccessException(-1, statusMessage), null);
//                            } else {
//                                callback.apply(null, response);
//                            }
//                        }
//                    } catch (IOException ex2) {
//                        callback.apply(ex2, null);
//                    }
//                });
//    }

    public static interface FileCallback {
        public void apply(Exception ex, File file);
    }

    public void downLoad(String downloadUrl, String filepath, FileCallback callback) {
        //this.downLoad(downloadUrl, true, filepath, callback);
    }

//    public void downLoad(String downloadUrl , final boolean showLoading, String filepath,FileCallback callback){
//
//        // 网络未连接  chentong
//        if(!NetworkUtil.isConnectingToInternet(this.context)){
//            callback.apply(new AccessException(-1, "未能连上网络"), null);
//            return;
//        }
//        Logger.d("下载地址:" + downloadUrl);
//        if (showLoading) this.loadingSubject.onNext(true);
//        Ion.with(this.context)
//                .load(downloadUrl)
//                .uploadProgressDialog(new ProgressDialog(this.context))
//                .progress(new ProgressCallback() {
//                    @Override
//                    public void onProgress(long downloaded, long total) {
//                        Log.d(TAG,"下载"+downloaded+"/"+total);
//                    }
//                })
//                .write(new File(filepath))
//                .setCallback((ex1,file)->{
//                    if (showLoading) this.loadingSubject.onNext(false);
//                    if (ex1 != null) {
//                        callback.apply(ex1, null);
//                        return;
//                    }
//                    Logger.d("接受文件:" + filepath);
//                    try {
//                        callback.apply(null, file);
//                    } catch (Exception ex2) {
//                        callback.apply(ex2, null);
//                    }
//                });
//
//    }

    /**
     * 取消所有的网络请求
     */
    public void cancelAllRequests() {
        try {
            OkHttpUtils.getInstance().cancelTag(CancelTag);
        } catch (Exception e) {
            Logger.d("取消请求错误:" + CancelTag + e.getLocalizedMessage());
        }
    }

    public static boolean isResponseSuccess(MobileMessage response) {
        int statusCode = response.getCode();
        if (statusCode == -1) return false;

        return statusCode == 0;
    }
}
