package com.tami.vmanager.http;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import rx.Scheduler;
import rx.android.schedulers.HandlerScheduler;

/**
 * Created by guoyu
 */
public class BaseBroker {

    public static final String TAG = BaseBroker.class.getSimpleName();

    protected static Handler ioHandler;
    protected Context context;

    public static void globalInit() {
        HandlerThread handlerThread = new HandlerThread("CFP_IO_PROCESS", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        BaseBroker.ioHandler = new Handler(handlerThread.getLooper());
    }

    public BaseBroker(Context context) {
        this.context = context;
    }

    /**
     *
     * @return IO调度器
     */
    protected Scheduler IOScheduler() {
        return HandlerScheduler.from(BaseBroker.ioHandler);
    }
}
