package com.tami.vmanager.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecyclerItemDecoration;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateMeetingRequest;
import com.tami.vmanager.entity.CreateMeetingResponse;
import com.tami.vmanager.entity.CreateVipGuestRequest;
import com.tami.vmanager.entity.GetMeetingAddressListResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.UploadImageRequest;
import com.tami.vmanager.entity.UploadImageResponse;
import com.tami.vmanager.entity.UserListOfPositionResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.FileSizeUtil;
import com.tami.vmanager.utils.GetImagePath;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.utils.Utils;
import com.tami.vmanager.view.CircleImageView;
import com.tami.vmanager.view.CreateMeetingBottomMenu;
import com.tami.vmanager.view.SwitchButton;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页中的创建会议
 * Created by why on 2018/6/13.
 */
public class CreateMeetingRewriteActivity extends BaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private Button saveBtn;//保存按钮
    //TOP侧面NAME-主要给TEXT文本框赋值*号
    private TextView nameTxtView;
    private TextView sponsorTxtView;
    private TextView meeetingPlaceTxtView;
    private TextView startTimeTxtView;
    private TextView endTimeTxtView;
    private TextView contractAmountTxtView;
    private TextView receivedAmountTxtView;
    //TOP侧面请输入，请选择
    private EditText nameView;
    private EditText sponsorView;
    private TextView meeetingPlaceView;
    private TextView startTimeView;
    private TextView endTimeView;
    private EditText contractAmountView;
    private EditText receivedAmountView;
    //参会人
    private TextView numberTxtView;
    private EditText estimatedNumberPeople;
    private EditText bottomNumberPeople;
    //会议等级
    private TextView meetingLevel;
    //接待人
    private RecyclerView receptionistList;
    private List<UserListOfPositionResponse.Item.TitleItem.ContentList> receptionistListData;
    private CommonAdapter<UserListOfPositionResponse.Item.TitleItem.ContentList> receptionistListAdapter;
    //Vip介绍
    private RecyclerView vipRecyclerView;
    private List<CreateVipGuestRequest> vipListData;
    private CommonAdapter<CreateVipGuestRequest> vipAdapter;
    //EO单图片
    private AppCompatImageView addImage;
    private AppCompatImageView deleteImage;
    private TextView imageName;
    private TextView imageSize;
    private Group imageGroup;//EO单管理隐藏和显示其组控件
    //V智会
    private SwitchButton switchButton;

    private Date recordStartDate;//记录开始时间
    private Date recordEndDate;//记录结束时间
    private int meetingLevelIndex = 0;//会议级别

    private NetworkBroker networkBroker;
    private final String IMAGE_TYPE = "image/*";
    private final int EO_DAN = 0X00;
    //会议地址ID
    private int addressId = -1;
    //图片路径
    private String filePath = null;

    public static final int REQUEST_EXTERNAL_STORAGE = 103;
    public static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_meeting_rewrite;
    }

    @Override
    public void initView() {
        saveBtn = findViewById(R.id.acmr_save_btn);
        //TOP侧面NAME
        nameTxtView = findViewById(R.id.acmr_meeting_name_txt);
        sponsorTxtView = findViewById(R.id.acmr_sponsor_txt);
        meeetingPlaceTxtView = findViewById(R.id.acmr_meeting_place_txt);
        startTimeTxtView = findViewById(R.id.acmr_start_time_txt);
        endTimeTxtView = findViewById(R.id.acmr_end_time_txt);
        contractAmountTxtView = findViewById(R.id.acmr_contract_amount_txt);
        receivedAmountTxtView = findViewById(R.id.acmr_received_amount_txt);
        //TOP侧面请输入，请选择
        nameView = findViewById(R.id.acmr_meeting_name);
        sponsorView = findViewById(R.id.acmr_sponsor);
        meeetingPlaceView = findViewById(R.id.acmr_meeting_place);
        startTimeView = findViewById(R.id.acmr_start_time);
        endTimeView = findViewById(R.id.acmr_end_time);
        contractAmountView = findViewById(R.id.acmr_contract_amount);
        receivedAmountView = findViewById(R.id.acmr_received_amount);
        //参会人员
        numberTxtView = findViewById(R.id.acmr_number_participants);
        estimatedNumberPeople = findViewById(R.id.acmr_estimated_number_people);
        bottomNumberPeople = findViewById(R.id.acmr_bottom_number);
        //会议等级
        meetingLevel = findViewById(R.id.acmr_meeting_level_chose);
        //接待人
        receptionistList = findViewById(R.id.acmr_receptionist_recyclerview);
        //Vip介绍
        vipRecyclerView = findViewById(R.id.acmr_personage_introduction_recyclerview);
        //上传EO单
        addImage = findViewById(R.id.acmr_eo_add_image);
        deleteImage = findViewById(R.id.acmr_eo_delete_image);
        imageName = findViewById(R.id.acmr_eo_add_image_name);
        imageSize = findViewById(R.id.acmr_eo_add_image_size);
        imageGroup = findViewById(R.id.acmr_eo_image_group);
        //V智会
        switchButton = findViewById(R.id.acmr_v_zhi_hui_switchbtn);
    }

    @Override
    public void initListener() {
        saveBtn.setOnClickListener(this);
        meeetingPlaceView.setOnClickListener(this);
        startTimeView.setOnClickListener(this);
        endTimeView.setOnClickListener(this);
        meetingLevel.setOnClickListener(this);
        addImage.setOnClickListener(this);
        deleteImage.setOnClickListener(this);

        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.create_meeting);
        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
        //文本加*操作
        initTextView();
        //初始化接待人列表
        initReceptionistList();
        //初始化VIP列表
        initVipRecyclerView();
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }

    @Override
    @AfterPermissionGranted(REQUEST_EXTERNAL_STORAGE)
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //权限请求与判断
            if (!EasyPermissions.hasPermissions(getApplicationContext(), PERMISSIONS_STORAGE)) {
                EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                        REQUEST_EXTERNAL_STORAGE, PERMISSIONS_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Constants.CREATE_MEETING_DIDIAN:
                    //开会地点选择返回
                    GetMeetingAddressListResponse.Array.Item item = (GetMeetingAddressListResponse.Array.Item) data.getSerializableExtra(Constants.RESULT_DIDIAN);
                    if (item != null && !TextUtils.isEmpty(item.name)) {
                        meeetingPlaceView.setText(item.name);
                        addressId = item.id;
                    }
                    break;
                case Constants.CREATE_MEETING_JIEDAIREN:
                    //接待人返回
                    List<UserListOfPositionResponse.Item.TitleItem.ContentList> itemList = data.getParcelableArrayListExtra(Constants.RESULT_JIEDAIREN);
                    if (itemList != null && itemList.size() > 0) {
                        for (UserListOfPositionResponse.Item.TitleItem.ContentList cl : itemList) {
                            boolean flag = true;
                            for (UserListOfPositionResponse.Item.TitleItem.ContentList rld : receptionistListData) {
                                if (cl.id == rld.id) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                receptionistListData.add(0, cl);
                            }
                        }
                        receptionistListAdapter.notifyDataSetChanged();
                    }
                    break;
                case Constants.CREATE_MEETING_VIP:
                    //VIP人物介绍返回
                    CreateVipGuestRequest cvgrItem = (CreateVipGuestRequest) data.getSerializableExtra(Constants.RESULT_VIP);
                    if (cvgrItem != null) {
                        int index = vipListData.size() - 1;
                        vipListData.add(index, cvgrItem);
                        vipAdapter.notifyDataSetChanged();
                    }
                    break;
                case EO_DAN:
                    //从相册选取照片后返回
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            Uri originalUri = data.getData(); // 获得图片的uri
                            Logger.d("originalUri : " + originalUri);
                            if (originalUri != null) {
                                addImage.setImageURI(originalUri);
                                filePath = GetImagePath.getPath(getApplicationContext(), originalUri);
                            }
                            if (!TextUtils.isEmpty(filePath)) {
                                File file = new File(filePath);
                                if (file.exists()) {
                                    imageName.setText(file.getName());
                                    imageSize.setText(FileSizeUtil.getAutoFileOrFilesSize(filePath));
                                    imageGroup.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.acmr_save_btn:
//                save();
                uploadImage();
                break;
            case R.id.acmr_meeting_place:
                meetingPlace();
                break;
            case R.id.acmr_start_time:
                startTime();
                break;
            case R.id.acmr_end_time:
                endTime();
                break;
            case R.id.acmr_meeting_level_chose:
                meetingLevel();
                break;
            case R.id.acmr_eo_add_image:
                addImage();
                break;
            case R.id.acmr_eo_delete_image:
                deleteImage();
                break;
        }
    }

    /**
     * 保存按钮
     */
    private void save() {
        createMeeting();
    }

    /**
     * 开会地点
     */
    private void meetingPlace() {
        startActivityForResult(new Intent(getApplicationContext(), MeetingPlaceSelectActivity.class), Constants.CREATE_MEETING_DIDIAN);
    }

    /**
     * 起始时间
     */
    private void startTime() {
        createTime(startTimeView, true, recordStartDate);
    }

    /**
     * 结束时间
     */
    private void endTime() {
        createTime(endTimeView, false, recordEndDate);
    }

    /**
     * 会议级别
     */
    private void meetingLevel() {
        CreateMeetingBottomMenu createMeetingBottomMenu = new CreateMeetingBottomMenu(this, meetingLevelIndex);
        createMeetingBottomMenu.setMenuOnClickListener(new CreateMeetingBottomMenu.MenuOnClickListener() {
            @Override
            public void menuOnClick(String name, int id) {
                meetingLevel.setText(name);
                meetingLevelIndex = id;
            }
        });
        createMeetingBottomMenu.show();
    }

    /**
     * 添加图片EO单
     */
    private void addImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), EO_DAN);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType(IMAGE_TYPE);
                startActivityForResult(openAlbumIntent, EO_DAN);
            }
        }
    }

    /**
     * 删除图片EO单
     */
    private void deleteImage() {
        addImage.setImageResource(R.mipmap.create_meeting_add);
        imageGroup.setVisibility(View.GONE);
        filePath = null;
    }


    /**
     * 初始化加星数据
     */
    private void initTextView() {
        setTextView(nameTxtView, "* 会议名称");
        setTextView(sponsorTxtView, "* 主办方");
        setTextView(meeetingPlaceTxtView, "* 开会地点");
        setTextView(startTimeTxtView, "* 开始时间");
        setTextView(endTimeTxtView, "* 结束时间");
        setTextView(contractAmountTxtView, "* 合同金额");
        setTextView(receivedAmountTxtView, "* 待收金额");
        setTextView(numberTxtView, "* 参会人数");
    }

    /**
     * 加星
     *
     * @param view
     * @param content
     */
    private void setTextView(TextView view, String content) {
        view.setText(Utils.getSplicingColor(getApplicationContext(), content, 0, 1, R.color.color_FF3131));
    }

    /**
     * 初始化接待人列表
     */
    private void initReceptionistList() {
        GridLayoutManager layoutManage = new GridLayoutManager(getApplicationContext(), 3);
        receptionistList.addItemDecoration(new RecyclerItemDecoration(ScreenUtil.dip2px(getApplicationContext(), 20), 3));
        receptionistList.setLayoutManager(layoutManage);
        receptionistListData = new ArrayList<>();
        UserListOfPositionResponse.Item.TitleItem.ContentList item = new UserListOfPositionResponse.Item.TitleItem.ContentList();
        item.id = -1000;
        receptionistListData.add(item);

        receptionistListAdapter = new CommonAdapter<UserListOfPositionResponse.Item.TitleItem.ContentList>(getApplicationContext(), R.layout.item_create_meeting_receptionist, receptionistListData) {
            @Override
            protected void convert(ViewHolder holder, UserListOfPositionResponse.Item.TitleItem.ContentList contentList, int position) {
                Group group = holder.getView(R.id.icmr_group);
                AppCompatImageView defaultImage = holder.getView(R.id.icmr_default_image);
                if (contentList.id == -1000) {
                    group.setVisibility(View.GONE);
                    defaultImage.setVisibility(View.VISIBLE);
                    defaultImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getApplicationContext(), AddReceptionistActivity.class), Constants.CREATE_MEETING_JIEDAIREN);
                        }
                    });
                    return;
                } else {
                    group.setVisibility(View.VISIBLE);
                    defaultImage.setVisibility(View.GONE);
                    CircleImageView avatarImage = holder.getView(R.id.icmr_avatar_image);
                    if (!TextUtils.isEmpty(contentList.iconUrl)) {
                        Picasso.get().load(contentList.iconUrl).into(avatarImage);
                    }
                    AppCompatImageView deleteImage = holder.getView(R.id.icmr_delete_image);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            receptionistListData.remove(position);
                            receptionistListAdapter.notifyDataSetChanged();
                        }
                    });
                    holder.setText(R.id.icmr_name, contentList.realName);
                }
            }
        };
        receptionistList.setAdapter(receptionistListAdapter);
    }

    /**
     * 初始化VIP列表
     */
    private void initVipRecyclerView() {
        GridLayoutManager layoutManage = new GridLayoutManager(getApplicationContext(), 3);
        vipRecyclerView.addItemDecoration(new RecyclerItemDecoration(ScreenUtil.dip2px(getApplicationContext(), 20), 3));
        vipRecyclerView.setLayoutManager(layoutManage);
        vipListData = new ArrayList<>();
        CreateVipGuestRequest createVipGuestRequest = new CreateVipGuestRequest();
        vipListData.add(createVipGuestRequest);
        vipAdapter = new CommonAdapter<CreateVipGuestRequest>(getApplicationContext(), R.layout.item_create_meeting_personage_introduction, vipListData) {
            @Override
            protected void convert(ViewHolder holder, CreateVipGuestRequest item, int position) {
                Group group = holder.getView(R.id.icmpi_group);
                AppCompatImageView defaultImage = holder.getView(R.id.icmpi_default_image);
                if (!TextUtils.isEmpty(item.getName())) {
                    group.setVisibility(View.VISIBLE);
                    defaultImage.setVisibility(View.GONE);
                    AppCompatImageView deleteImage = holder.getView(R.id.icmpi_delete);
                    deleteImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            vipListData.remove(position);
                            vipAdapter.notifyDataSetChanged();
                        }
                    });
                    holder.setText(R.id.icmpi_name, item.getName());
                } else {
                    group.setVisibility(View.GONE);
                    defaultImage.setVisibility(View.VISIBLE);
                    defaultImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getApplicationContext(), VIPPersonageIntroductionActivity.class), Constants.CREATE_MEETING_VIP);
                        }
                    });
                    return;
                }
            }
        };
        vipRecyclerView.setAdapter(vipAdapter);
    }

    /**
     * 弹出时间框
     *
     * @param view     点击哪个VIEW
     * @param dataFlag true起始时间false结束时间
     * @param date     记录的起始时间或结束时间
     */
    private void createTime(final TextView view, final boolean dataFlag, Date date) {
        Calendar selectedDate = Calendar.getInstance();
        if (date != null) {
            selectedDate.setTime(date);
        }
        final Calendar startDate = Calendar.getInstance();
        final Calendar endDate = Calendar.getInstance();
//        startDate.set(2018, 0, 1);
        endDate.set(2020, 11, 31);
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (dataFlag) {
                    recordStartDate = date;
                } else {
                    recordEndDate = date;
                }
                view.setText(TimeUtils.date2String(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isDialog(true)//是否显示为对话框样式
                .isCyclic(true)//是否循环滚动
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
        pvTime.show(view);
    }

    /**
     * 创建会议
     */
    private void createMeeting() {
        if (!isEmpty()) {
            return;
        }
        if (!TextUtils.isEmpty(filePath)) {
            uploadImage();
            return;
        }
        createMeetingRequest(null);
    }

    /**
     * 请求网络
     */
    private void createMeetingRequest(String eoUrl) {
        CreateMeetingRequest cmr = new CreateMeetingRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        cmr.setSystemId(item.getSystemId());
        cmr.setMeetingName(nameView.getText().toString());
        cmr.setSponsorName(sponsorView.getText().toString());
        cmr.setCreateMeetingUserId(item.getId());
        cmr.setMeetingAddressId(String.valueOf(addressId));
        cmr.setStartDate(TimeUtils.date2String(recordStartDate));
        cmr.setEndDate(TimeUtils.date2String(recordEndDate));
        cmr.setContractMoney(contractAmountView.getText().toString());
        cmr.setPayMoney(receivedAmountView.getText().toString());
        cmr.setEstimateNum(estimatedNumberPeople.getText().toString());
        cmr.setMinNum(bottomNumberPeople.getText().toString());
        cmr.setIsImportant(String.valueOf(meetingLevelIndex));
        if (receptionistListData != null && receptionistListData.size() > 0) {
            cmr.setVipReceiveUserId(getStringIds());
        }
        if (!TextUtils.isEmpty(eoUrl)) {
            cmr.setEoUrl(eoUrl);
        }
        cmr.setIsVzh(String.valueOf(switchButton.isChecked() ? 1 : 0));
        if (vipListData != null && vipListData.size() > 0) {
            cmr.setVipList(getVipList());
        }
        networkBroker.ask(cmr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                CreateMeetingResponse response = (CreateMeetingResponse) res;
                if (response.getCode() == 200) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 接待人员ID
     */
    private String getStringIds() {
        StringBuilder builder = new StringBuilder();
        for (UserListOfPositionResponse.Item.TitleItem.ContentList contentList : receptionistListData) {
            if (contentList.id != -1000) {
                builder.append(contentList.id);
                builder.append(",");
            }
        }
        return builder.substring(0, builder.length() - 1).toString();
    }

    /**
     * 获取VIP内容
     *
     * @return
     */
    private List<CreateMeetingRequest.Item> getVipList() {
        List<CreateMeetingRequest.Item> listIemt = new ArrayList<>();
        for (CreateVipGuestRequest cvgr : vipListData) {
            CreateMeetingRequest.Item item = new CreateMeetingRequest.Item();
            item.setSystemId(cvgr.getSystemId());
            item.getName(cvgr.getName());
            item.setIntro(cvgr.getIntro());
            listIemt.add(item);
        }
        return listIemt;
    }

    /**
     * 为空验证
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(nameView.getText())
                || TextUtils.isEmpty(sponsorView.getText())
                || addressId == -1
                || recordStartDate == null
                || recordEndDate == null
                || TextUtils.isEmpty(contractAmountView.getText())
                || TextUtils.isEmpty(receivedAmountView.getText())
                || TextUtils.isEmpty(estimatedNumberPeople.getText())
                || TextUtils.isEmpty(bottomNumberPeople.getText())
                ) {
            showToast("必添项不可为空！");
            return false;
        }
        return true;
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        UploadImageRequest fmm = new UploadImageRequest();
        fmm.filePath = new String[]{filePath};
        networkBroker.uploadImage(fmm, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                UploadImageResponse response = (UploadImageResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        UploadImageResponse.Item item = response.data;
                        if (item.dataList != null && item.dataList.size() > 0) {
                            Logger.d("上传图片返回地址：" + item.dataList.get(0));
                            createMeetingRequest(item.dataList.get(0));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }
}