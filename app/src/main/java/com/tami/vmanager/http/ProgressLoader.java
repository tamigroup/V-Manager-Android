package com.tami.vmanager.http;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tami.vmanager.R;

/**
 * 装载进度器
 */
public class ProgressLoader {
    public static Dialog create(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);

        // 得到加载view
        View loadingView = inflater.inflate(R.layout.dialog_loading, null);

        // 加载布局
        LinearLayout layout = (LinearLayout) loadingView.findViewById(R.id.loadingContainer);
        ImageView loadingImageView = (ImageView) loadingView.findViewById(R.id.loadingImageView);

        // 加载动画
        Animation loadingAnimation = AnimationUtils.loadAnimation(context, R.anim.progress_loading);

        // 使用ImageView显示动画
        loadingImageView.startAnimation(loadingAnimation);

        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

        // 不可以用"返回键"取消
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }
}