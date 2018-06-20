package com.tami.vmanager.application;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.squareup.picasso.Picasso;

/**
 * Created by why on 2018/6/11.
 */
public class TaMiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getApplicationContext());
    }

    private void init() {
        initPicasso();
    }

    private void initPicasso() {

    }
}
