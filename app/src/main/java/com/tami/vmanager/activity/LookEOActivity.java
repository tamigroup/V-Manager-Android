package com.tami.vmanager.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.view.photoview.PhotoView;

/**
 * 查看EO单
 * Created by why on 2018/7/5.
 */
public class LookEOActivity extends BaseActivity {

    private String eoUrl;
    private PhotoView photoView;
    private TextView textView;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_look_eo;
    }

    @Override
    public void initView() {
        photoView = findViewById(R.id.ale_image);
        textView = findViewById(R.id.not_uploaded_eo_list);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.look_eo));
        Intent intent = getIntent();
        if (intent != null) {
            eoUrl = intent.getStringExtra(Constants.KEY_EO_URL);
            if (!TextUtils.isEmpty(eoUrl)) {
                textView.setVisibility(View.GONE);
                Picasso.get().load(eoUrl).into(photoView);
            } else {
                textView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        photoView.setBackgroundResource(0);
        photoView = null;
    }
}
