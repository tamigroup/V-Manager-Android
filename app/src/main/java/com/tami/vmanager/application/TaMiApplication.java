package com.tami.vmanager.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.readystatesoftware.chuck.ChuckInterceptor;
//import com.squareup.leakcanary.LeakCanary;
import com.tami.vmanager.BuildConfig;
import com.tami.vmanager.utils.Constants;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import okhttp3.OkHttpClient;

/**
 * Created by why on 2018/6/11.
 */
public class TaMiApplication extends Application {

    public static String registrationID;
    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();
    }

    public static Application getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    private void init() {
        initOkHttp();

        initBugly();

        initJIM();

        initLeakCanary();

        initStetho();

//        initCrashHandler();
    }

    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }


    private void initJIM() {
        //初始化IM
        JMessageClient.setDebugMode(BuildConfig.DEBUG);
        JMessageClient.init(getApplicationContext(), true);
        //初始化推送
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        registrationID = JPushInterface.getRegistrationID(this);
    }

    private void initBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel("tencent");
        strategy.setAppVersion("1.0");
        strategy.setAppPackageName("com.tami.vmanager");
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), "93294bd277", BuildConfig.DEBUG, strategy);
        Bugly.init(this, "93294bd277", BuildConfig.DEBUG, strategy);
    }

    private void initOkHttp() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ChuckInterceptor(this))
                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new LoggerInterceptor(Constants.LOG_TAG, true))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    private void initLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
