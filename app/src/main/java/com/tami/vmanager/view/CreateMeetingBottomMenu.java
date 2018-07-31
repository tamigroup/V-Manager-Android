package com.tami.vmanager.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;

import com.tami.vmanager.R;

/**
 * Created by why on 2018/6/29.
 */
public class CreateMeetingBottomMenu extends BottomSheetDialog implements View.OnClickListener {

    private MenuOnClickListener menuOnClickListener;

    public CreateMeetingBottomMenu(Context context, int selectId) {
        super(context);
        ConstraintLayout constraintLayout = (ConstraintLayout) LayoutInflater.from(context).inflate(R.layout.show_menu_meeting_level, null);
        constraintLayout.findViewById(R.id.smml_vip1).setOnClickListener(this);
        constraintLayout.findViewById(R.id.smml_vip2).setOnClickListener(this);
        constraintLayout.findViewById(R.id.smml_vip3).setOnClickListener(this);
        constraintLayout.findViewById(R.id.smml_vip4).setOnClickListener(this);
        constraintLayout.findViewById(R.id.smml_cancel).setOnClickListener(this);
        setSelectId(constraintLayout, selectId);
        setContentView(constraintLayout);
    }

    /**
     * 设置选中项
     *
     * @param constraintLayout
     * @param selectId
     */
    private void setSelectId(ConstraintLayout constraintLayout, int selectId) {
        AppCompatImageView imageView = null;
        switch (selectId) {
            case 1:
                imageView = constraintLayout.findViewById(R.id.smml_vip1_selected);
                break;
            case 2:
                imageView = constraintLayout.findViewById(R.id.smml_vip2_selected);
                break;
            case 3:
                imageView = constraintLayout.findViewById(R.id.smml_vip3_selected);
                break;
            case 4:
                imageView = constraintLayout.findViewById(R.id.smml_vip4_selected);
                break;
        }
        if (imageView != null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (menuOnClickListener == null) {
            dismiss();
            return;
        }
        switch (v.getId()) {
            case R.id.smml_vip1:
            case R.id.smml_vip1_selected:
                menuOnClickListener.menuOnClick("VIP 1", 1);
                break;
            case R.id.smml_vip2:
            case R.id.smml_vip2_selected:
                menuOnClickListener.menuOnClick("VIP 2", 2);
                break;
            case R.id.smml_vip3:
            case R.id.smml_vip3_selected:
                menuOnClickListener.menuOnClick("VIP 3", 3);
                break;
            case R.id.smml_vip4:
            case R.id.smml_vip4_selected:
                menuOnClickListener.menuOnClick("VIP 4", 4);
                break;
            case R.id.smml_cancel:
                menuOnClickListener.menuOnClick("", 0);
                break;
        }
        dismiss();
    }

    public void setMenuOnClickListener(MenuOnClickListener menuOnClickListener) {
        this.menuOnClickListener = menuOnClickListener;
    }

    public interface MenuOnClickListener {
        public void menuOnClick(String name, int id);
    }
}
