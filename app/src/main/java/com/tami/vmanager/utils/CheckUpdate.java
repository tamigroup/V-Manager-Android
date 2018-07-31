package com.tami.vmanager.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.tami.vmanager.R;
import com.tami.vmanager.entity.UpdateBean;
import com.tami.vmanager.enums.UpdateType;
import com.tami.vmanager.http.HttpKey;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.ActivityManager;

/**
 * Created by Tang on 2018/7/24
 * 检测更新
 */
public class CheckUpdate {
    private UIData uiData = null;
    private UpdateBean.DataBean data;
    private DownloadBuilder builder;
    private Dialog dialog;

    //不能单例
    public static CheckUpdate getInstance(Activity context, int num) {
        return new CheckUpdate(context, num);
    }

    private CheckUpdate(Activity context, int num) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("versionId", Utils.getLocalVersionName(context));
        httpParams.put("appId", 1);   //appId 1是Android
        String requestUrl = NetworkBroker.BASE_URI + HttpKey.UPDATE;
        builder = AllenVersionChecker.getInstance()
                .requestVersion()
                .setRequestUrl(requestUrl)
                .setRequestMethod(HttpRequestMethod.POSTJSON)
                .setRequestParams(httpParams)
                .request(new RequestVersionListener() {
                    @Nullable
                    @Override
                    public UIData onRequestVersionSuccess(String result) {
                        // String json = Utils.getJson(context, "cars.json");
                        // UpdateBean updateBean = JsonUtils.getInstance().fromJson(json, UpdateBean.class);
                        UpdateBean updateBean = JsonUtils.getInstance().fromJson(result, UpdateBean.class);
                        if (updateBean.getCode() == 200) {
                            data = updateBean.getData();
                            checkUpdate(context, num);
                        }
                        return uiData;
                    }

                    @Override
                    public void onRequestVersionFailure(String message) {
                    }
                });
        builder.excuteMission(context);
    }

    /**
     * 自定义dialog布局
     *
     * @param i   i=1普通更新 i=2强制更新
     * @param num num=0 是HomeActivity num=1是设置点击
     */
    private CustomVersionDialogListener updateDialog(int i, int num) {
        return (context, versionBundle) -> {
            dialog = new Dialog(context, R.style.BaseDialog);
            dialog.setContentView(R.layout.dialog_update_layout);
            TextView tv_title = dialog.findViewById(R.id.tv_title);
            TextView tv_msg = dialog.findViewById(R.id.tv_msg);
            TextView dialog_commit = dialog.findViewById(R.id.versionchecklib_version_dialog_commit);
            if (i == 2) {
                dialog.findViewById(R.id.versionchecklib_version_dialog_cancel).setVisibility(View.GONE);
            }
            if (num == 0) {
                dialog_commit.setText(R.string.now_update);
            } else if (num == 1) {
                dialog_commit.setText(R.string.update_now);
            }
            tv_title.setText(versionBundle.getTitle());
            tv_msg.setText(versionBundle.getContent());
            dialog.setCanceledOnTouchOutside(false);
            return dialog;
        };
    }

    /**
     * 检测更新
     *
     * @param context this
     * @param num     num=0 是HomeActivity num=1是设置点击
     */
    private void checkUpdate(Activity context, int num) {
        if (data != null) {
            if (data.getMustUpdate() == UpdateType.noUpdate.getType()) {
                if (num == 1) {
                    Toast.makeText(context.getApplicationContext(), R.string.latest_version, Toast.LENGTH_SHORT).show();
                }
            } else if (data.getMustUpdate() == UpdateType.update.getType()) {
                crateUIData();
                builder.setCustomVersionDialogListener(updateDialog(1, num));
            } else if (data.getMustUpdate() == UpdateType.forceUpdate.getType()) {
                crateUIData();
                builder.setCustomVersionDialogListener(updateDialog(2, num));
                builder.setForceUpdateListener(() -> ActivityManager.getInstance().finishAllActivity());
            }
            builder.setCustomDownloadingDialogListener(createDownloadingDialog());
        }
    }

    private void crateUIData() {
        uiData = UIData
                .create()
                .setDownloadUrl(data.getUpdateUrl())
                .setTitle(data.getTitle())
                .setContent(data.getContent());
    }

    /**
     * 自定义下载中对话框，下载中会连续回调此方法 updateUI
     * 务必用库传回来的context 实例化你的dialog
     *
     * @return
     */
    private CustomDownloadingDialogListener createDownloadingDialog() {
        return new CustomDownloadingDialogListener() {
            @Override
            public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                Dialog dialog_update_download = new Dialog(context, R.style.BaseDialog);
                dialog_update_download.setContentView(R.layout.dialog_update_download);
                return dialog_update_download;
            }

            @Override
            public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                TextView tvProgress = dialog.findViewById(R.id.tv_progress);
                ProgressBar progressBar = dialog.findViewById(R.id.pb);
                progressBar.setProgress(progress);
                tvProgress.setText(progress + "%");
            }
        };
    }
}
