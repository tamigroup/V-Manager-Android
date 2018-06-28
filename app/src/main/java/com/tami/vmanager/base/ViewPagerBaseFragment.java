package com.tami.vmanager.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.base.inter.IBaseFragment;


/**
 * Created by lixishuang on 2017/11/30.
 */
public abstract class ViewPagerBaseFragment extends Fragment implements IBaseFragment, View.OnClickListener {

    public View view;
    private TextView titleName;
    private ImageView titleLeftBtn;
    private ImageView titleRightBtn;
    private TextView titleRightTxt;
    private ProgressDialog progressDialog;
    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    public boolean isTitle() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = isTitle() ? getContentTitelView(inflater) : getContentView(inflater, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            requestNetwork();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    @Override
    public void onDestroyView() {
        if (isTitle()) {
            titleName = null;
            titleLeftBtn = null;
            titleRightBtn = null;
            titleRightTxt = null;
        }
        dialogDismiss();
        progressDialog = null;
        view = null;
        emptyObject();
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        if (titleLeftBtn != null && v.getId() == R.id.titleLeftBtn) {
            getActivity().finish();
        }
    }

    @Override
    public void onDestroy() {
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
        removeListener();
        super.onDestroy();
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getContentViewId(), container, false);
    }

    @Override
    public View getContentTitelView(LayoutInflater inflater) {
        LinearLayout superLayout = (LinearLayout) inflater.inflate(R.layout.title, null);
        initTitleView(superLayout);
        inflater.inflate(getContentViewId(), superLayout);
        return superLayout;
    }

    @Override
    public void initTitleView(View view) {
        titleName = findViewById(view, R.id.titleName);
        titleLeftBtn = findViewById(view, R.id.titleLeftBtn);
        titleLeftBtn.setOnClickListener(this);
        titleRightBtn = findViewById(view, R.id.titleRightBtn);
        titleRightTxt = findViewById(view, R.id.titleRightTxt);
    }

    @Override
    public void setTitleName(@NonNull String content) {
        if (titleName != null) {
            titleName.setText(content);
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleName(@StringRes int resId) {
        if (titleName != null) {
            titleName.setText(getString(resId));
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleLeftBtn(@DrawableRes int resId) {
        if (titleLeftBtn != null) {
            titleLeftBtn.setImageResource(resId);
            titleLeftBtn.setOnClickListener(this);
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightBtn(@DrawableRes int resId) {
        if (titleRightBtn != null) {
            titleRightBtn.setVisibility(View.VISIBLE);
            titleRightBtn.setImageResource(resId);
            titleRightBtn.setOnClickListener(this);
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightTxt(@StringRes int resId) {
        if (titleRightTxt != null) {
            titleRightTxt.setVisibility(View.VISIBLE);
            titleRightTxt.setText(getString(resId));
            titleRightTxt.setOnClickListener(this);
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void setTitleRightTxt(@NonNull String txt) {
        if (titleRightTxt != null) {
            titleRightTxt.setVisibility(View.VISIBLE);
            titleRightTxt.setText(txt);
            titleRightTxt.setOnClickListener(this);
        } else {
            showToast(getString(R.string.isTitleMethodReturnsTrue));
        }
    }

    @Override
    public void showToast(@NonNull String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(@StringRes int resId) {
        Toast.makeText(getContext(), getString(resId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.ceshi));
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
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public <T extends View> T findViewById(@NonNull View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }

    public void setTitleLeftBtnVisibility(int visibility) {
        if (titleLeftBtn != null) {
            titleLeftBtn.setVisibility(visibility);
        }
    }

    public  String getTAG() {
        return this.getClass().getSimpleName();
    }
}
