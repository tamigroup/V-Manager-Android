package com.tami.vmanager.fragment;

import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseFragment;
import com.tami.vmanager.view.CircleImageView;

/**
 * Created by why on 2018/6/12.
 */

public class PersonalCenterFragment extends BaseFragment {

    private CircleImageView avatar_image;//我的头像
    private TextView full_name;//姓名
    private TextView position;//部门-职务
    private TextView my_creation;//我的创建
    private TextView account_settings;//帐号设置
    private BottomSheetDialog mBottomSheetDialog;

    @Override
    public boolean isTitle() {
        return false;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initView() {
        avatar_image = findViewById(R.id.personal_center_avatar_image);
        full_name = findViewById(R.id.personal_center_full_name);
        position = findViewById(R.id.personal_center_position);
        my_creation = findViewById(R.id.personal_center_my_creation);
        account_settings = findViewById(R.id.personal_center_account_settings);
    }

    @Override
    public void initListener() {
        avatar_image.setOnClickListener(this);
        my_creation.setOnClickListener(this);
        account_settings.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog.setContentView(null);
            mBottomSheetDialog = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.personal_center_avatar_image:
                //头像
                avatarImage();
                break;
            case R.id.personal_center_my_creation:
                //我的创建
                myCreation();
                break;
            case R.id.personal_center_account_settings:
                //帐户设置
                accountSettings();
                break;
            case R.id.personal_menu_album:
                //相册
                mBottomSheetDialog.dismiss();
                break;
            case R.id.personal_menu_photograph:
                //拍照
                mBottomSheetDialog.dismiss();
                break;
            case R.id.personal_menu_cancel:
                //取消
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    /**
     * 头像设置
     */
    private void avatarImage() {
        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        LinearLayout linearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.item_personal_menu, null);
        TextView album = linearLayout.findViewById(R.id.personal_menu_album);
        TextView photograph = linearLayout.findViewById(R.id.personal_menu_photograph);
        TextView cancel = linearLayout.findViewById(R.id.personal_menu_cancel);
        album.setOnClickListener(this);
        photograph.setOnClickListener(this);
        cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(linearLayout);
        mBottomSheetDialog.show();
    }

    /**
     * 我的创建
     */
    private void myCreation() {

    }

    /**
     * 帐号设置
     */
    private void accountSettings() {

    }
}
