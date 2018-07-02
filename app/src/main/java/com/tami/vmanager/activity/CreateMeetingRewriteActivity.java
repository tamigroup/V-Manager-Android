package com.tami.vmanager.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
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
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecyclerItemDecoration;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.CreateVipGuestRequest;
import com.tami.vmanager.entity.MeetingPlaceSelectResponse;
import com.tami.vmanager.entity.UserListOfPositionResponse;
import com.tami.vmanager.utils.Constants;
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

/**
 * 首页中的创建会议
 * Created by why on 2018/6/13.
 */
public class CreateMeetingRewriteActivity extends BaseActivity implements View.OnClickListener {

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
    private List<String> receptionistListData;
    private CommonAdapter<String> receptionistListAdapter;
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

    private final String IMAGE_TYPE = "image/*";
    private final int EO_DAN = 0X00;

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Constants.CREATE_MEETING_DIDIAN:
                    Logger.d("开会地点选择返回-------->");
                    MeetingPlaceSelectResponse.Item item = (MeetingPlaceSelectResponse.Item) data.getSerializableExtra(Constants.RESULT_DIDIAN);
                    if (item != null && !TextUtils.isEmpty(item.getName())) {
                        meeetingPlaceView.setText(item.getName());
                    }
                    break;
                case Constants.CREATE_MEETING_JIEDAIREN:
                    Logger.d("接待人返回-------->");
                    List<UserListOfPositionResponse.Item.TitleItem.ContentList> itemList = data.getParcelableArrayListExtra(Constants.RESULT_JIEDAIREN);
                    Logger.d("itemList:" + itemList.size());
                    break;
                case Constants.CREATE_MEETING_VIP:
                    Logger.d("VIP人物介绍返回-------->");
                    CreateVipGuestRequest cvgrItem = (CreateVipGuestRequest) data.getSerializableExtra(Constants.RESULT_VIP);
                    if (cvgrItem != null) {
                        int index = vipListData.size() - 1;
                        vipListData.add(index, cvgrItem);
                        vipAdapter.notifyDataSetChanged();
                    }
                    break;
                case EO_DAN:
                    Logger.d("从相册选取照片后返回....");
                    if (resultCode == RESULT_OK) {
                        if (data != null) {
                            String filePath = "";
                            Uri originalUri = data.getData(); // 获得图片的uri
                            Logger.d("originalUri : " + originalUri);
                            if (originalUri != null) {
                                addImage.setImageURI(originalUri);
                                filePath = GetImagePath.getPath(getApplicationContext(), originalUri);
                            }
                            Logger.d("filePath : " + filePath);
                            File sdCard = Environment.getExternalStorageDirectory();
                            File file = new File(sdCard,filePath);
                            Logger.d("filePath.length() : " + filePath.length());
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
                save();
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
        createTime(endTimeView, true, recordEndDate);
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
        receptionistListData.add("");
        receptionistListAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.item_create_meeting_receptionist, receptionistListData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                Group group = holder.getView(R.id.icmr_group);
                AppCompatImageView defaultImage = holder.getView(R.id.icmr_default_image);
                if (receptionistListData.size() - 1 != position) {
                    group.setVisibility(View.VISIBLE);
                    defaultImage.setVisibility(View.GONE);
                } else {
                    group.setVisibility(View.GONE);
                    defaultImage.setVisibility(View.VISIBLE);
                    defaultImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivityForResult(new Intent(getApplicationContext(), AddReceptionistActivity.class), Constants.CREATE_MEETING_JIEDAIREN);
                        }
                    });
                    return;
                }
                CircleImageView avatarImage = holder.getView(R.id.icmr_avatar_image);
                AppCompatImageView deleteImage = holder.getView(R.id.icmr_delete_image);
                holder.setText(R.id.icmr_name, s);
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
                if (vipListData.size() - 1 != position) {
                    group.setVisibility(View.VISIBLE);
                    defaultImage.setVisibility(View.GONE);
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
                AppCompatImageView deleteImage = holder.getView(R.id.icmpi_delete);
                deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vipListData.remove(position);
                        vipAdapter.notifyDataSetChanged();
                    }
                });
                holder.setText(R.id.icmpi_name, item.getName());
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
                view.setText(TimeUtils.date2String(date, TimeUtils.DATE_YYYYMMDDHHMM_SLASH));
            }
        }).setType(new boolean[]{true, true, true, true, true, false})
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
}