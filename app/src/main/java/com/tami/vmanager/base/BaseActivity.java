package com.tami.vmanager.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.R.id;
import com.tami.vmanager.R.string;
import com.tami.vmanager.base.inter.IBaseActivity;
import com.tami.vmanager.manager.ActivityManager;

/**
 * Created by lixishuang on 2017/11/30.
 */
public abstract class BaseActivity extends FragmentActivity implements IBaseActivity, View.OnClickListener {

    private LayoutInflater layoutInflater;
    private TextView titleName;
    private ImageView titleLeftBtn;
    private ImageView titleRightBtn;
    private TextView titleRightTxt;
    private ProgressDialog progressDialog;
    private boolean firstLoad = true;//只加载一次网络请求

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(ContextCompat.getColor(getApplicationContext(), getStartBarColor()));
            if (!tintManager.isNavBarTintEnabled()) {
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setNavigationBarTintColor(ContextCompat.getColor(getApplicationContext(), getNavigationBarColor()));
            }
        }
        //设置主题颜色
        Window window = getWindow();
        //设置背景为NULL
        window.setBackgroundDrawable(null);
        layoutInflater = LayoutInflater.from(this);
        //判断是否有Title布局并添充
        if (getContentViewId() == 0) {
            Toast.makeText(getApplicationContext(), getString(R.string.activity_is_layout), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setContentView(isTitle() ? getContentTitelView() : getContentView());
        initProgressDialog();
        initView();
        initListener();
        initData();
        ActivityManager.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        if (titleLeftBtn != null && v.getId() == id.titleLeftBtn) {
            ActivityManager.getInstance().finishActivity(this);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (firstLoad) {
            firstLoad = false;
            requestNetwork();
        }
    }

    @Override
    protected void onDestroy() {
        removeListener();
        emptyObject();
        layoutInflater = null;
        if (isTitle()) {
            titleName = null;
            titleLeftBtn = null;
            titleRightBtn = null;
            titleRightTxt = null;
        }
        dialogDismiss();
        progressDialog = null;
        super.onDestroy();
    }

    @Override
    public View getContentView() {
        return layoutInflater.inflate(getContentViewId(), null);
    }

    @Override
    public View getContentTitelView() {
        LinearLayout superLayout = (LinearLayout) layoutInflater.inflate(R.layout.title, null);
        initTitleView(superLayout);
        //没有获取到相应的布局结束当前Activity
        layoutInflater.inflate(getContentViewId(), superLayout);
        return superLayout;
    }

    @Override
    public void initTitleView(View view) {
        titleName = view.findViewById(id.titleName);
        titleLeftBtn = view.findViewById(id.titleLeftBtn);
        titleLeftBtn.setOnClickListener(this);
        titleRightBtn = view.findViewById(id.titleRightBtn);
        titleRightTxt = view.findViewById(id.titleRightTxt);
    }

    @Override
    public void setTitleName(@NonNull String content) {
        if (titleName != null) {
            titleName.setText(content);
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleName(@StringRes int resId) {
        if (titleName != null) {
            titleName.setText(getString(resId));
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleLeftBtn(@DrawableRes int resId) {
        if (titleLeftBtn != null) {
            titleLeftBtn.setImageResource(resId);
            titleLeftBtn.setOnClickListener(this);
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightBtn(@DrawableRes int resId) {
        if (titleRightBtn != null) {
            titleRightBtn.setVisibility(View.VISIBLE);
            titleRightBtn.setImageResource(resId);
            titleRightBtn.setOnClickListener(this);
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightTxt(@StringRes int resId) {
        if (titleRightTxt != null) {
            titleRightTxt.setVisibility(View.VISIBLE);
            titleRightTxt.setText(getString(resId));
            titleRightTxt.setOnClickListener(this);
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightTxt(@NonNull String txt) {
        if (titleRightTxt != null) {
            titleRightTxt.setVisibility(View.VISIBLE);
            titleRightTxt.setText(txt);
            titleRightTxt.setOnClickListener(this);
        } else {
            showToast(getString(string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getApplicationContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(string.ceshi));
        progressDialog.setCancelable(false);
    }

    @Override
    public void dialogShow() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dialogDismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public int getStartBarColor() {
        return R.color.colorPrimary;
    }

    @Override
    public int getNavigationBarColor() {
        return R.color.colorPrimary;
    }
}
