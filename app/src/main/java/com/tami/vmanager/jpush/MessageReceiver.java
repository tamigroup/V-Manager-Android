package com.tami.vmanager.jpush;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.ConferenceServiceGroupActivity;
import com.tami.vmanager.activity.HomeActivity;
import com.tami.vmanager.activity.IdeasBoxActivity;
import com.tami.vmanager.activity.MeetingOverviewActivity;
import com.tami.vmanager.application.TaMiApplication;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.JsonUtils;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Tang on 2018/7/25
 * <p>
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MessageReceiver extends BroadcastReceiver {

    //    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            // if (null == manager) {
            //     manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            // }

            Bundle bundle = intent.getExtras();
            Logger.d("[极光MessageReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                //用户注册成功
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d("[极光MessageReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                //接受到推送下来的自定义消息
                Logger.d("[极光MessageReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                //在通知到达时触发
                Logger.d("[极光MessageReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d("[极光MessageReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                //当用户点击时触发
                Logger.d("[极光MessageReceiver] 用户点击打开了通知 是否在前台=="+ TaMiApplication.isBackground(context));
                Logger.d("[极光MessageReceiver] 用户点击打开了通知");
                String Jpush_key = (String) SPUtils.get(context, Constants.JPUSH_KEY, "");
                int jpush_meetingid = (int) SPUtils.get(context, Constants.JPUSH_MEETINGID, 0);
                int jpush_noticemessageid = (int) SPUtils.get(context, Constants.JPUSH_NOTICEMESSAGEID, 0);
                Logger.d("[极光MessageReceiver] 用户点击打开了通知 Jpush_key" + Jpush_key);
                Logger.d("[极光MessageReceiver] 用户点击打开了通知 jpush_meetingid" + jpush_meetingid);
                Logger.d("[极光MessageReceiver] 用户点击打开了通知 jpush_noticemessageid" + jpush_noticemessageid);

                // needChange代表主办方需求变化
                // groupMessage代表群消息
                // opinionCase标识为意见箱评价 满意度
                // userTask标识为分配服务节点 创建会议  会议概览页
                // createMeeting updateMeeting setMeetingItem 会议概览页
                // noticeMessage,55,234  群公告推送 55代表会议ID  234代表公告ID

                if (TaMiApplication.isBackground(context)){
                    //在后台
                    //跳到HomeActivity
                    Intent i = new Intent(context, HomeActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                    inStartFront(context, Jpush_key, jpush_meetingid, jpush_noticemessageid);
                    Logger.d("app在后台==");
                }else {
                    inStartFront(context, Jpush_key, jpush_meetingid, jpush_noticemessageid);
                    Logger.d("app在前台==");
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d("[极光MessageReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.d("[极光MessageReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d("[极光MessageReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {
            Logger.d("[极光MessageReceiver] Exception --- " + e.getMessage());
        }

    }

    /**
     * app处于前台可见
     */
    private void inStartFront(Context context, String jpush_key, int jpush_meetingid, int jpush_noticemessageid) {
        if (TextUtils.isEmpty(jpush_key)) {
            //跳到HomeActivity
            Intent i = new Intent(context, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
        } else if (jpush_key.equals("needChange")) {
            //跳到活动变化
//            Intent changeDemandIntent = new Intent(context, ChangeDemandActivity.class);
//            changeDemandIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
//            changeDemandIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            context.startActivity(changeDemandIntent);
            Intent ConferenceServiceGroupIntent= new Intent(context,ConferenceServiceGroupActivity.class);
            ConferenceServiceGroupIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
            ConferenceServiceGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(ConferenceServiceGroupIntent);
        }else if (jpush_key.equals("groupMessage")){
            //跳到会议服务群 群消息
            Intent ConferenceServiceGroupIntent= new Intent(context,ConferenceServiceGroupActivity.class);
            ConferenceServiceGroupIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
            ConferenceServiceGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(ConferenceServiceGroupIntent);
        } else if (jpush_key.equals("noticeMessage")){
            //跳到会议服务群 群公告
            Intent ConferenceServiceGroupIntent= new Intent(context,ConferenceServiceGroupActivity.class);
            ConferenceServiceGroupIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
            ConferenceServiceGroupIntent.putExtra(Constants.JPUSH_NOTICEMESSAGEID,jpush_noticemessageid);
            ConferenceServiceGroupIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(ConferenceServiceGroupIntent);
        }else if (jpush_key.equals("opinionCase")) {
            //跳到满意度
            Intent IdeasBoxIntent= new Intent(context,IdeasBoxActivity.class);
            IdeasBoxIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
            IdeasBoxIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(IdeasBoxIntent);
        } else if (jpush_key.equals("userTask")|| jpush_key.equals("createMeeting")|| jpush_key.equals("updateMeeting")|| jpush_key.equals("setMeetingItem")) {
            //跳到会议概览页
            Intent changeDemandIntent = new Intent(context, MeetingOverviewActivity.class);
            changeDemandIntent.putExtra(Constants.KEY_MEETING_ID,jpush_meetingid);
            changeDemandIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(changeDemandIntent);
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to
    private void processCustomMessage(Context context, Bundle bundle) {
        if (null != bundle) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

            Logger.e("extra===" + extra + " title===" + title + " message====" + message);
            if (!TextUtils.isEmpty(extra)) {
                ExtraBean extraBean = JsonUtils.getInstance().fromJson(extra, ExtraBean.class);
                String[] split = extraBean.getMsgExtrasKey().split(",");
                String startKey = split[0];
                int meetingId = Integer.parseInt(split[1]);
                //是群公告 获取公告Id
                if (startKey.equals("noticeMessage")){
                    int noticeMessageId = Integer.parseInt(split[2]);
                    SPUtils.put(context, Constants.JPUSH_NOTICEMESSAGEID, noticeMessageId);
                    Logger.e("noticeMessageId===" + noticeMessageId);
                }
                Logger.e("startKey===" + startKey);
                Logger.e("meetingId===" + meetingId);
                SPUtils.put(context, Constants.JPUSH_KEY, startKey);
                SPUtils.put(context, Constants.JPUSH_MEETINGID, meetingId);
            }
            BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
            builder.statusBarDrawable = R.mipmap.icon;
            builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为自动消失
            builder.notificationDefaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS; // 设置为铃声与震动都要
            JPushInterface.setDefaultPushNotificationBuilder(builder);
            JPushInterface.setPushNotificationBuilder(1, builder);


            // 自定义Notification
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //     NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
            //     channel.enableLights(true);
            //     channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            //     channel.setLightColor(Color.RED);
            //     channel.canShowBadge();
            //     channel.enableVibration(true);
            //     channel.shouldShowLights();
            //     manager.createNotificationChannel(channel);
            // }
            // Intent intent = new Intent();
            // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            // Notification notification = builder
            //         .setContentTitle(title)
            //         .setContentText(message)
            //         .setWhen(System.currentTimeMillis())
            //         .setSmallIcon(R.mipmap.icon)
            //         .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            //         .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            //         .setPriority(NotificationCompat.PRIORITY_MAX)
            //         .setDefaults(NotificationCompat.DEFAULT_ALL)
            //         .setFullScreenIntent(pendingIntent, true)
            //         .setAutoCancel(true)
            //         .setChannelId("channel_id")
            //         .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon))
            //         .build();
            // manager.notify(1, notification);
        }
    }
}
