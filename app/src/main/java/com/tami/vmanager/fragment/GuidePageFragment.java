package com.tami.vmanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import com.tami.vmanager.R;
import com.tami.vmanager.activity.LoginActivity;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.utils.Constants;

/**
 * Created by why on 2018/6/11.
 */

public class GuidePageFragment extends BaseFragment {

    private AppCompatImageView imageView;
    private Button skip_btn;
    private Button immediate_experience;

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
        skip_btn = findViewById(R.id.guidepage_skip_btn);
        immediate_experience = findViewById(R.id.guidepage_immediate_experience);
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
            boolean skipBtnFlag = bundle.getBoolean(Constants.SKIP_BTN);
            if (skipBtnFlag) {
                immediate_experience.setVisibility(View.GONE);
                skip_btn.setVisibility(View.VISIBLE);
                skip_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
            }
            boolean experienceFlag = bundle.getBoolean(Constants.EXPERIENCE);
            if (experienceFlag) {
                skip_btn.setVisibility(View.GONE);
                immediate_experience.setVisibility(View.VISIBLE);
                immediate_experience.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
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
        imageView = null;
    }

    public int getDrawableId(String key) {
        return getActivity().getResources().getIdentifier(key, "mipmap", getActivity().getPackageName());
    }
}
