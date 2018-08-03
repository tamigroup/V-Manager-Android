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
    public static final String USER_SEND_VERIFY_CODE_LOGIN = "sms/sendVerifyCodeLogin";
    //找回密码的验证码
    public static final String USER_SEND_VERIFY_CODE = "sms/sendVerifyCode";
    //注销登录
    public static final String USER_LOGIN_OUT = "user/loginOut";
    // 更改密码
    public static final String USER_CHANGE_PASSWORD = "user/changePassWord";
    // 重置密码
    public static final String USER_RESET_PASSOWRD = "user/resetPwd";
    //提醒消息设置
    public static final String USER_NOTICE_CONFIG = "user/setUserNoticeConfig";
    //获取提醒消息
    public static final String USER_GET_NOTICE_CONFIG = "user/getUserNoticeConfig";
    //修改用户头像
    public static final String USER_UPDATE_USER_ICON = "user/updateUserIcon";
    // 首页顶部数据
    public static final String USER_GET_BANNER_DATA = "meeting/getBannerData";
    // 首页下面数据
    public static final String USER_GET_INDEX = "meeting/getIndex";
    //  查询全部会议列表searchType类型1：所有，2：待付款，3：待完善，4：今天，5：本月，6：本年度
    public static final String USER_ALL_MEETINGS = "meeting/allMeetings";
    // 查询今日会议列表
    public static final String TODAY_MEETINGS = "meeting/todayMeetings";
    // 查询我关注的会议列表searchType1：全部，2:今天，3：一周，4：一个月
    public static final String FOLLOW_MEETINGS = "meeting/followMeetings";
    // 查询待办会议列表
    public static final String WAIT_MEETINGS = "meeting/waitMeetings";
    // 关注某一个会议
    public static final String FOLLOW_USER_MEETING = "meeting/followUserMeeting";
    // 取消关注某一个会议
    public static final String CANCEL_USER_MEETING = "meeting/cancelUserMeeting";
    //添加VIP成员信息
    public static final String CREATE_VIPGUEST = "vip/createVipGuest";
    //获取所有职位及职位下的人员列表
    public static final String USER_LIST_OF_POSITION = "user/userListOfPosition";
    // App创建会议
    public static final String CREATE_MEETING = "meeting/createMeeting";
    // 根据酒店ID查询会议厅List
    public static final String GET_MEETING_ADDRESS_LIST = "meeting/getMeetingAddressList";
    // 查询系统内置的会议流程所有节点
    public static final String GET_MEETING_ITEMS = "meeting/getMeetingItems";
    // 图片上传
    public static final String UPLOAD_IMAGE = "upload/uploadImage";
    //搜索会议
    public static final String SEARCH_MEETING = "meeting/searchMeetingPage";
    //获取反馈信息列表
    public static final String FEED_BACK = "chat/feedBackPage";
    //分页查询公告列表
    public static final String GET_NOTICE_LIST = "notice/noticePage";
    //查询会议概览信息/查看EO单
    public static final String GET_MEETING = "meeting/getMeeting";
    //根据会议ID查询会议节点信息
    public static final String GET_MEETING_ITEMS_BY_MEETING_ID = "meeting/getMeetingItemsByMeetingId";

    // 综合分数
    public static final String GET_AVG = "meetingEvaluate/getAvg";
    // 意见箱 评价列表
    public static final String GET_EVALUATEPAGE_LIST = "meetingEvaluate/evaluatePage";
    // VIP详情
    public static final String VIP_DETAIL = "vip/vipGusetPage";
    //创建公告
    public static final String CREATE_NOTICE = "notice/createNotice";
    //获取会议群组中的用户成员
    public static final String MEETING_USER_GROUP_PAGE = "group/meetingUserGroupPage";
    //群消息列表
    public static final String GET_MEETING_GROUP_CHAT = "chat/meetingChatPage";
    //发送群消息
    public static final String SEND_CHAT_MSG = "chat/sendMsg";
    // 查询我创建的会议列表
    public static final String MY_MEETINGS = "meeting/myMeetings";
    // 获取会议流程单
    public static final String GET_MEETING_ITEM_FLOW = "meeting/getMeetingItemFlow";
    // 获取会议流程单
    public static final String DELETE_USER_MEETING = "meeting/deleteUserMeeting";
    // 获取流程天数
    public static final String GET_MEETING_DAYS = "meeting/getMeetingDays";
    //获取需求变化(活动变化)分页数据
    public static final String GET_MEETING_REQUIREMENT_PAGE = "meeting/meetingRequirementPage";
    // 回复会议变化(活动变化)需求
    public static final String REPLY_MEETING_REQUIREMENT = "meeting/replyMeetingRequirement";
    // 系统内置角色List
    public static final String GET_SYSTEM_ROLE_LIST = "user/getSystemRoleList";
    // 创建自定义流程节点
    public static final String CREATE_USER_MEETING_ITEM = "meeting/createUserMeetingItem";
    // 添加（ 编辑）会议流程节点
    public static final String SET_MEETING_ITEMS = "meeting/setMeetingItems";
    // 修改会议信息
    public static final String UPDATE_MEETING = "meeting/updataMeeting";
    // 获取会议签到人员信息接口 人员名单
    public static final String GET_ACTUALLIST = "meeting/getActualList";
    // 主办方成员列表(来源于VZH)
    public static final String GET_SPONSOR_USER_LIST = "meeting/getSponsorUserList";
    // 验证用户是否已被分配了某节点权限
    public static final String IS_CAN_OPERATION = "meeting/isCanOperation";
    //会议流程节点分配人员
    public static final String SET_MEETING_ITEM_USER = "meeting/setMeetingItemsUser";
    //获取会议流程节点已分配人员
    public static final String GET_SELECT_MEETING_ITEMS_USER = "meeting/getSelectMeetingItemsUser";
    //获取全部部门用户组
    public static final String GET_USER_IN_DEPARTMENT = "user/getUserInDepartment";
    //获取部门用户组
    public static final String GET_USER_DEPARTMENT = "user/getUserDepartment ";
    //获取会议实到人数(来源于VZH)
    public static final String GET_ACTUAL_NUM = "meeting/getActualNum";
    //判断用户是否有添加会议节点用户的权限
    public static final String CHECK_ADD_MEETING_ITEM_USER = "meeting/checkAddMeetingItemUser";
    //会议流程节点删除人员操作权限
    public static final String DELETE_MEETING_ITEMS_USER = "meeting/deleteMeetingItemsUser";
    //会议流程节点删除人员操作权限
    public static final String SET_MEETING_ITEMS_STATUS = "meeting/setMeetingItemsStatus";
    //版本更新
    public static final String UPDATE = "upgrade/getUpgrade";
    //添加用户到会议分组(进群)
    public static final String INTOGROUPUSER = "group/IntoGroupUser";
    //从会议分组中删除用户(退群)
    public static final String OUTGROUPUSER = "group/outGroupUser";
    //校验用户是否在群中
    public static final String CHECKGROUPUSER = "group/checkGroupUser";
    //获取创建会议人的Id
    public static final String GETMEETINGSELL_USERID = "meeting/getMeetingSellUserId ";
    //清除极光registrationId
    public static final String longOutForRegistrationId = "user/longOutForRegistrationId ";



    //---------------------------------------------------------------------------

    //    //手机号
    //    public static final String KEY_MOBILE = "mobile ";
    //    //用户ID
    //    public static final String KEY_USER_ID = "userId";
    //    //回调极光函数得到的RegistrationId
    //    public static final String KEY_REGISTRATION_ID = "registrationId";
    //    //密码
    //    public static final String KEY_PASSWORD = "passWord";
    //    //旧密码
    //    public static final String KEY_OLD_PASSWORD = "oldPassWord";
    //    //新密码
    //    public static final String KEY_NEW_PASSWORD = "newPassWord";
    //    //短信验证码
    //    public static final String KEY_SMS_CODE = "smsCode";
    //    //机构ID
    //    public static final String KEY_SYSTEM_ID = "systemId";
    //    //系统消息 1 开启 0 未开启  以下设置同理
    //    public static final String KEY_SYSTEM_NOTICE = "systemNotice";
    //    //主办方消息
    //    public static final String KEY_HOST_NOTICE = "hostNotice";
    //    //会务广播
    //    public static final String KEY_MEETING_NOTICE = "meetingNotice";
    //    //满意度通知
    //    public static final String KEY_SATIS_FACTION_NOTICE = "satisfactionNotice";
    //    //群聊通知
    //    public static final String KEY_GROUP_CHAT_NOTICE = "groupChatNotice";
    //    //头像地址
    //    public static final String KEY_ICON_URL = "iconUrl";
    //    //会议名称
    //    public static final String KEY_MEETING_NAME = "meetingName";
    //    //会议创建者ID
    //    public static final String KEY_CREATE_MEETING_URER_ID = "createMeetingUserId";
    //    //会议厅ID
    //    public static final String KEY_MEETING_ADDRESS_ID = "meetingAddressId";
    //    //开始时间
    //    public static final String KEY_START_DATE = "startDate";
    //    //结束时间
    //    public static final String KEY_END_DATE = "endDate";
    //    //合同金额
    //    public static final String KEY_CONTRACT_MONEY = "contractMoney";
    //    //已付金额
    //    public static final String KEY_PAY_MONEY = "payMoney";
    //    //预计人数
    //    public static final String KEY_ESTIMATE_NUM = "estimateNum";
    //    //保底人数
    //    public static final String KEY_MIN_NUM = "minNum";
    //    //主办方名称
    //    public static final String KEY_SPONSOR_NAME = "sponsorName";
    //    //VIP等级  0 ，1，2，3，4
    //    public static final String KEY_IS_IMPORTANT = "isImportant";
    //    //接待人员ID集合 "1,2,3"
    //    public static final String KEY_VIP_RECEIVE_USER_ID = "vipReceiveUserId";
    //    //EO单地址
    //    public static final String KEY_EO_URL = "eoUrl";
    //    //是否V智会  0不是  1是
    //    public static final String KEY_IS_VZH = "isVzh";
    //    //VIP参会人员LIST[{"name":"",“intro”:""},{"name":"",“intro”:""}]
    //    public static final String KEY_VIP_LIST = "vipList";
    //    //会议ID
    //    public static final String KEY_MEETING_ID = "meetingId";
    //    //VIP嘉宾姓名
    //    public static final String KEY_NAME = "name";
    //    //VIP嘉宾介绍
    //    public static final String KEY_INTRO = "intro";
    //    //当前页
    //    public static final String KEY_CURPAGE = "curPage";
    //    //每页条数
    //    public static final String KEY_PAGE_SIZE = "pageSize";
    //    // 类别  参数固定为0，1，2  【 0-今天  1-本月  2-本年】
    //    public static final String KEY_TYPE = "type";
    //    // 类型 	1：全部，2:今天，3：一周，4：一个月 //如果是会意意见箱类别  2-举办方  3-参会方
    //    public static final String KEY_SEARCH_TYPE = "searchType";
    //    // 会议举办时间 年月日，格式如 2018-05-21
    //    public static final String KEY_START_ON = "startOn";
    //    // 流程节点ID，开始时间集合 json字符串  格式参考说明
    //    public static final String KEY_MEETING_ITEMS_JSON = "meetingItemsJson";
    //    // 流程节点ID
    //    public static final String KEY_MEETING_ITEM_SET_ID = "meetingItemSetId";
    //    // 设置的状态值  1代表通过  2代表有问题 3代表正常状态（灰色）
    //    public static final String KEY_STATUS = "status";
    //    // 节点有问题时的备注
    //    public static final String KEY_MARK = "mark";
    //    // 需求方人员ID
    //    public static final String KEY_REQUEST_USER_ID = "requestUserId";
    //    // 内容
    //    public static final String KEY_REQUEST_CONTENT = "requestContent";
    //    // 发布人姓名
    //    public static final String KEY_REQUEST_USER_NAME = "requestUserName";
    //    // 发布人头像
    //    public static final String KEY_REQUEST_ICON_URL = "requestIconUrl";
    //    // 需求变化 ID
    //    public static final String KEY_MEETING_REQUIREMENT_ID = "MeetingRequirementId";
    //    // 回复人ID
    //    public static final String KEY_REPLY_USER_ID= "replyUserId";
    //    // 回复内容
    //    public static final String KEY_REPLY_CONTENT= "replyContent";
    //    // 评价内容
    //    public static final String KEY_CONTENT= "content";
    //    // 分数  5
    //    public static final String KEY_SCORE= "score";
    //    // 评论人真实姓名
    //    public static final String KEY_USER_NAME= "userName";
    //    // 评论ID
    //    public static final String KEY_ID= "id";
    //    // 群组类型	 1-V管家 2-V智会 3-VV群
    //    public static final String KEY_GROUP_TYPE= "groupType";
    //    // 公告类型    0-全部  1-系统通知  2-会务广播 3-群内公告
    //    public static final String KEY_NOTICE_TYPE= "noticeType";
    //    // 公告标题
    //    public static final String KEY_TITLE= "title";
    //    // 是否置顶   0 正常    1 置顶
    //    public static final String KEY_IS_TOP= "isTop";
    //    //公告ID
    //    public static final String KEY_NOTICE_ID= "noticeId";
    //    //用户头像(当群消息来自v智会  或者 来自vv群时需传userIcon)
    //    public static final String KEY_USER_ICON= "userIcon";
    //    //从哪里发送  1-v管家  2-v智会 3-VV群
    //    public static final String KEY_FROM_TYPE= "fromType";
    //    //发送到哪里  1-v管家 2-v智会 3-VV群
    //    public static final String KEY_TO_TYPE= "toType";

}
