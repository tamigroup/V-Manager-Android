package com.tami.vmanager.base.inter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lixishuang on 2017/11/30.
 */
public interface IBaseFragment {

    /**
     * 判断Fragment是否需要显示Title
     *
     * @return boolean
     */
    public boolean isTitle();

    /**
     * 获取内容显示布局的资源ID
     *
     * @return int
     */
    public int getContentViewId();

    /**
     * 获取无Title的布局
     *
     * @return
     */
    public View getContentView(LayoutInflater inflater, ViewGroup container);

    /**
     * 获取有Title的布局
     *
     * @return View
     */
    public View getContentTitelView(LayoutInflater inflater);

    /**
     * 初始化Title布局
     *
     * @param view
     */
    public void initTitleView(View view);

    /**
     * 设置Title名称
     *
     * @param content
     */
    public void setTitleName(@NonNull String content);

    /**
     * 设置Title名称
     *
     * @param resId
     */
    public void setTitleName(@StringRes int resId);

    /**
     * 设置Title左边反回按钮
     *
     * @param resId
     */
    public void setTitleLeftBtn(@DrawableRes int resId);

    /**
     * 设置Title右边功能按钮
     *
     * @param resId
     */
    public void setTitleRightBtn(@DrawableRes int resId);

    /**
     * 设置Title右边文本功能
     *
     * @param resId
     */
    public void setTitleRightTxt(@StringRes int resId);

    /**
     * 设置Title右边文本功能
     *
     * @param txt
     */
    public void setTitleRightTxt(@NonNull String txt);

    /**
     * 内容布局初始View
     */
    public void initView();

    /**
     * 内容布局初始监听
     */
    public void initListener();

    /**
     * 内容布局初始数据初始化
     */
    public void initData();

    /**
     * 内容布局网络请求初始化
     */
    public void requestNetwork();

    /**
     * 内容布局初始监听移除
     */
    public void removeListener();

    /**
     * 内容布局对象清空
     */
    public void emptyObject();

    /**
     * 提示消息
     *
     * @param msg
     */
    public void showToast(@NonNull String msg);

    /**
     * 提示消息
     *
     * @param resId
     */
    public void showToast(@StringRes int resId);

    /**
     * 初始化弹出框
     */
    public void initProgressDialog();

    /**
     * 弹出网络请求提示框
     */
    public void dialogShow();

    /**
     * 清除提示框
     */
    public void dialogDismiss();

    /**
     * 获取View
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@IdRes int id);

    /**
     * 获取View
     *
     * @param view
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(@NonNull View view, @IdRes int id);

}
