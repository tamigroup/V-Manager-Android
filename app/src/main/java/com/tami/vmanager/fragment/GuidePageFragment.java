package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.LoginActivity;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.utils.Constants;

/**
 * Created by why on 2018/6/11.
 */

public class GuidePageFragment extends ViewPagerBaseFragment {

    private AppCompatImageView imageView;

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_guidepage;
    }

    @Override
    public void initView() {
        imageView = findViewById(R.id.guidepage_image_id);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String key = bundle.getString(Constants.IMAGE_KEY);
            int imageId = getDrawableId(key);
            imageView.setImageResource(imageId);
            boolean experienceFlag = bundle.getBoolean(Constants.EXPERIENCE);
            if (experienceFlag) {
                imageView.setOnClickListener((View view) -> {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                });
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
        imageView.setBackgroundResource(0);
        imageView = null;
    }

    public int getDrawableId(String key) {
        return getActivity().getResources().getIdentifier(key, "mipmap", getActivity().getPackageName());
    }
}
