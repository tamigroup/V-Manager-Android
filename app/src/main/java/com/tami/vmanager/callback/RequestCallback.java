package com.tami.vmanager.callback;


import com.google.gson.Gson;
import com.tami.vmanager.utils.Logger;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by why on 2018/6/20.
 */
@Deprecated
public abstract class RequestCallback<T> extends Callback<T> {

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        String string = response.body().string();
        Logger.d("RequestCallback:" + string);
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T t = new Gson().fromJson(string, entityClass);
        return t;
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Logger.d("onError:------------------------->");
        e.printStackTrace();
        onError();
    }

    @Override
    public void onResponse(T response, int id) {
        onSuccess(response);
    }

    public abstract void onSuccess(T response);

    public abstract void onError();

}
