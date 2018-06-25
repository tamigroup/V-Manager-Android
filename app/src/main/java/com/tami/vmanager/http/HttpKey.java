package com.tami.vmanager.http;

/**
 * Created by why on 2018/6/23.
 */

public class HttpKey {

    //用户名和密码登录
    public static final String USER_LOGIN = "user/login";
    //手机号和验证码登录
    public static final String USER_LOGIN_SMS = "user/loginSms";
    // 绑定用户RegistrationId
    public static final String USER_REGISTRATION_ID = "user/setUserRegistrationId";
    //请求发送验证码
    public static final String USER_SEND_VERIFY_CODE = "sms/sendVerifyCode";
    //注销登录
    public static final String USER_LOGIN_OUT = "user/loginOut";
    // 更改密码
    public static final String USER_CHANGE_PASSWORD = "user/changePassWord";
    // 重置密码
    public static final String USER_RESET_PASSOWRD = "user/resetPwd";
    //获取部门用户组
    public static final String USER_GET_USER_DEPARTMENT = "user/getUserInDepartment";
    //提醒消息设置
    public static final String USER_NOTICE_CONFIG = "user/setUserNoticeConfig";
    //获取提醒消息
    public static final String USER_GET_NOTICE_CONFIG = "user/getUserNoticeConfig";
    //获取用户信息
    public static final String USER_APP_USER = "user/getAppUser";
    //修改用户头像
    public static final String USER_UPDATE_USER_ICON = "user/updateUserIcon";
    // App创建会议
    public static final String USER_CREATE_MEETING = "meeting/createMeeting";


    //---------------------------------------------------------------------------

    //手机号
    public static final String KEY_MOBILE = "mobile ";
    //用户ID
    public static final String KEY_USER_ID = "userId";
    //回调极光函数得到的RegistrationId
    public static final String KEY_REGISTRATION_ID = "registrationId";
    //密码
    public static final String KEY_PASSWORD = "passWord";
    //旧密码
    public static final String KEY_OLD_PASSWORD = "oldPassWord";
    //新密码
    public static final String KEY_NEW_PASSWORD = "newPassWord";
    //短信验证码
    public static final String KEY_SMS_CODE = "smsCode";
    //机构ID
    public static final String KEY_SYSTEM_ID = "systemId";
    //系统消息 1 开启 0 未开启  以下设置同理
    public static final String KEY_SYSTEM_NOTICE = "systemNotice";
    //主办方消息
    public static final String KEY_HOST_NOTICE = "hostNotice";
    //会务广播
    public static final String KEY_MEETING_NOTICE = "meetingNotice";
    //满意度通知
    public static final String KEY_SATIS_FACTION_NOTICE = "satisfactionNotice";
    //群聊通知
    public static final String KEY_GROUP_CHAT_NOTICE = "groupChatNotice";


}
