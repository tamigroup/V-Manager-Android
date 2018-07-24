package com.tami.vmanager.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecyclerItemDecoration;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
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
import com.tami.vmanager.utils.ImageUtils;
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
public class CreateMeetingRewriteActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks, View.OnFocusChangeListener {

    private Button saveBtn;//保存按钮
    //TOP侧面NAME-主要给TEXT文本框赋值*号
    private TextView nameTxtView;
    private TextView sponsorTxtView;
    private TextView meeetingPlaceTxtView;
    private TextView startTimeTxtView;
    private TextView endTimeTxtView;
    //    private TextView contractAmountTxtView;
//    private TextView receivedAmountTxtView;
    //TOP侧面请输入，请选择
    private EditText nameView;
    private EditText sponsorView;
    private TextView meeetingPlaceView;
    private TextView startTimeView;
    private TextView endTimeView;
    //    private EditText contractAmountView;
//    private EditText receivedAmountView;
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
    //    private TextView imageName;
    private TextView imageSize;
    private Group imageGroup;//EO单管理隐藏和显示其组控件
    //V智会
    private SwitchButton switchButton;

    private Date recordStartDate;//记录开始时间
    private Date recordEndDate;//记录结束时间
    private int meetingLevelIndex = 0;//会议级别

    private NetworkBroker networkBroker;
    //会议地址ID
    private int addressId = -1;
    //图片路径
    private String filePath = null;
    public static final String HEAD_ICON_DIC = Environment.getExternalStorageDirectory() + File.separator + "TaMiExternal";
    protected final String TAG = getClass().getSimpleName();
    private File eoFile = null;// 相册或者拍照保存的文件
    private String eoFileNameStr = "eodan.jpg";
    private Uri imageUri;
    private final String IMAGE_TYPE = "image/*";
    private final int EO_DAN_XIANGCE = 0X13;
    private final int EO_DAN_PAIZHAO = 0X14;
    public static final int REQUEST_EXTERNAL_STORAGE = 103;
    public static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int REQUEST_CAMERA = 104;
    public static final String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};
    private BottomSheetDialog mBottomSheetDialog;

    //保存弹框
    private ConfirmEnterMeetingDialog cemd;
    private int meetingId = -1;
    //时间框
    private TimePickerView pvTime;

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
//        contractAmountTxtView = findViewById(R.id.acmr_contract_amount_txt);
//        receivedAmountTxtView = findViewById(R.id.acmr_received_amount_txt);
        //TOP侧面请输入，请选择
        nameView = findViewById(R.id.acmr_meeting_name);
        sponsorView = findViewById(R.id.acmr_sponsor);
        meeetingPlaceView = findViewById(R.id.acmr_meeting_place);
        startTimeView = findViewById(R.id.acmr_start_time);
        endTimeView = findViewById(R.id.acmr_end_time);
//        contractAmountView = findViewById(R.id.acmr_contract_amount);
//        receivedAmountView = findViewById(R.id.acmr_received_amount);
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
//        imageName = findViewById(R.id.acmr_eo_add_image_name);
        imageSize = findViewById(R.id.acmr_eo_add_image_size);
        imageGroup = findViewById(R.id.acmr_eo_image_group);
        //V智会
        switchButton = findViewById(R.id.acmr_v_zhi_hui_switchbtn);

        cemd = new ConfirmEnterMeetingDialog(this);
        cemd.setContentRes(R.string.whether_create_process);
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

        nameView.setOnFocusChangeListener(this);
        sponsorView.setOnFocusChangeListener(this);
        estimatedNumberPeople.setOnFocusChangeListener(this);
        bottomNumberPeople.setOnFocusChangeListener(this);

        switchButton.setOnCheckedChangeListener((SwitchButton view, boolean isChecked) -> {

        });

        cemd.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void leftBtn() {
                setResult(Constants.CREATE_MEETING);
                finish();
            }

            @Override
            public void rightBtn() {
                Intent flowIntent = new Intent(getApplicationContext(), CreateServiceFlowActivity.class);
                flowIntent.putExtra(Constants.KEY_MEETING_ID, meetingId);
                startActivityForResult(flowIntent, Constants.CREATE_FLOW);
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
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
            mBottomSheetDialog.setContentView(null);
            mBottomSheetDialog = null;
        }
        if (cemd != null && cemd.isShowing()) {
            cemd.dismiss();
        }
        cemd = null;
        if (pvTime != null && pvTime.getDialog() != null && pvTime.getDialog().isShowing()) {
            pvTime.getDialog().dismiss();
        }
        pvTime = null;
        if (receptionistListData != null) {
            receptionistListData.clear();
            receptionistListData = null;
        }
        if (vipListData != null) {
            vipListData.clear();
            vipListData = null;
        }
        addImage.setBackgroundResource(0);
        deleteImage.setBackgroundResource(0);
        recordStartDate = null;
        recordEndDate = null;
        eoFile = null;
        imageUri = null;
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    @Override
    @AfterPermissionGranted(REQUEST_EXTERNAL_STORAGE)
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //权限请求与判断
            if (EasyPermissions.hasPermissions(this, PERMISSIONS_STORAGE)) {
                initHeadIconFile();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                        REQUEST_EXTERNAL_STORAGE, PERMISSIONS_STORAGE);
            }
        } else {
            initHeadIconFile();
        }
    }

    private void initHeadIconFile() {
        eoFile = new File(HEAD_ICON_DIC);
        Log.e(TAG, "initHeadIconFile()---headIconFile.exists() : " + eoFile.exists());
        if (!eoFile.exists()) {
            boolean mkdirs = eoFile.mkdirs();
            Log.e(TAG, "initHeadIconFile()---mkdirs : " + mkdirs);
        }
        eoFile = new File(HEAD_ICON_DIC, eoFileNameStr);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.CREATE_MEETING_DIDIAN:
                if (data != null) {
                    //开会地点选择返回
                    GetMeetingAddressListResponse.Array.Item item = (GetMeetingAddressListResponse.Array.Item) data.getSerializableExtra(Constants.RESULT_DIDIAN);
                    if (item != null && !TextUtils.isEmpty(item.name)) {
                        meeetingPlaceView.setText(item.name);
                        addressId = item.id;
                    }
                }
                break;
            case Constants.CREATE_MEETING_JIEDAIREN:
                //接待人返回
                if (data != null) {
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
                }
                break;
            case Constants.CREATE_MEETING_VIP:
                if (data != null) {
                    //VIP人物介绍返回
                    CreateVipGuestRequest cvgrItem = (CreateVipGuestRequest) data.getSerializableExtra(Constants.RESULT_VIP);
                    if (cvgrItem != null) {
                        int index = vipListData.size() - 1;
                        vipListData.add(index, cvgrItem);
                        vipAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case EO_DAN_XIANGCE:
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
                            //图片压缩
                            filePath = ImageUtils.compressImage(filePath);
                            File file = new File(filePath);
                            if (file.exists()) {
//                                imageName.setText(file.getName());
                                imageSize.setText(FileSizeUtil.getAutoFileOrFilesSize(filePath));
                                imageGroup.setVisibility(View.VISIBLE);
                                addPicToGallery(filePath);
                            }
                        }
                    }
                }
                break;
            case EO_DAN_PAIZHAO:
                //拍照
                //图片压缩
                filePath = ImageUtils.compressImage(eoFile.getAbsolutePath());
                File pzFile = new File(filePath);
                if (pzFile.exists()) {
                    addImage.setImageBitmap(ImageUtils.revitionImageSize(filePath));
//                    imageName.setText(pzFile.getName());
                    imageSize.setText(FileSizeUtil.getAutoFileOrFilesSize(filePath));
                    imageGroup.setVisibility(View.VISIBLE);
                    addPicToGallery(filePath);
                }
                break;
            case Constants.CREATE_FLOW:
                //创建流程返回
                setResult(Constants.CREATE_MEETING);
                finish();
                break;
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
            case R.id.personal_menu_album:
                //相册
                mBottomSheetDialog.dismiss();
                xiangCe();
                break;
            case R.id.personal_menu_photograph:
                //拍照
                mBottomSheetDialog.dismiss();
                paiZhao();
                break;
            case R.id.personal_menu_cancel:
                //取消
                mBottomSheetDialog.dismiss();
                break;
        }
    }

    /**
     * 保存按钮
     */
    private void save() {
        saveBtn.setEnabled(false);
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
        if(recordEndDate == null){
            createTime(endTimeView, false, recordStartDate);
        }else{
            createTime(endTimeView, false, recordEndDate);
        }
    }

    /**
     * 会议级别
     */
    private void meetingLevel() {
        CreateMeetingBottomMenu createMeetingBottomMenu = new CreateMeetingBottomMenu(this, meetingLevelIndex);
        createMeetingBottomMenu.setMenuOnClickListener((String name, int id) -> {
            meetingLevel.setText(name);
            meetingLevelIndex = id;
        });
        createMeetingBottomMenu.show();
    }

    /**
     * 添加图片EO单
     */
    private void addImage() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.show_menu_personal_pic, null);
        TextView album = linearLayout.findViewById(R.id.personal_menu_album);
        TextView photograph = linearLayout.findViewById(R.id.personal_menu_photograph);
        TextView cancel = linearLayout.findViewById(R.id.personal_menu_cancel);
        album.setOnClickListener(this);
        photograph.setOnClickListener(this);
        photograph.setVisibility(View.VISIBLE);
        cancel.setOnClickListener(this);
        mBottomSheetDialog.setContentView(linearLayout);
        mBottomSheetDialog.show();
    }

    private void xiangCe() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), EO_DAN_XIANGCE);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType(IMAGE_TYPE);
                startActivityForResult(openAlbumIntent, EO_DAN_XIANGCE);
            }
        }
    }

    @AfterPermissionGranted(REQUEST_CAMERA)
    private void paiZhao() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (EasyPermissions.hasPermissions(this, PERMISSIONS_CAMERA)) {
                openCamera();
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                        REQUEST_CAMERA, PERMISSIONS_CAMERA);
            }
        } else {
            openCamera();
        }
    }


    /**
     * 把图像添加进系统相册
     *
     * @param imgPath 图像路径
     */
    private void addPicToGallery(String imgPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imgPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    /**
     * 打开系统摄像头拍照获取图片
     */
    private void openCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                imageUri = Uri.fromFile(eoFile);
            } else {
                //FileProvider为7.0新增应用间共享文件,在7.0上暴露文件路径会报FileUriExposedException
                //为了适配7.0,所以需要使用FileProvider,具体使用百度一下即可
                imageUri = FileProvider.getUriForFile(this,
                        "com.tami.vmanager.fileprovider", eoFile);//通过FileProvider创建一个content类型的Uri
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, EO_DAN_PAIZHAO);
            Log.e(TAG, "openCamera()---intent" + intent);
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
        setTextView(nameTxtView, getString(R.string.jiaxing_1));
        setTextView(sponsorTxtView, getString(R.string.jiaxing_2));
        setTextView(meeetingPlaceTxtView, getString(R.string.jiaxing_3));
        setTextView(startTimeTxtView, getString(R.string.jiaxing_4));
        setTextView(endTimeTxtView, getString(R.string.jiaxing_5));
//        setTextView(contractAmountTxtView, getString(R.string.jiaxing_6));
//        setTextView(receivedAmountTxtView, getString(R.string.jiaxing_7));
        setTextView(numberTxtView, getString(R.string.jiaxing_8));
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
                    defaultImage.setOnClickListener((View v) -> {
                        startActivityForResult(new Intent(getApplicationContext(), AddReceptionistActivity.class), Constants.CREATE_MEETING_JIEDAIREN);
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
                    deleteImage.setOnClickListener((View v) -> {
                        receptionistListData.remove(position);
                        receptionistListAdapter.notifyDataSetChanged();
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
                    deleteImage.setOnClickListener((View v) -> {
                        vipListData.remove(position);
                        vipAdapter.notifyDataSetChanged();
                    });
                    holder.setText(R.id.icmpi_name, item.getName());
                } else {
                    group.setVisibility(View.GONE);
                    defaultImage.setVisibility(View.VISIBLE);
                    defaultImage.setOnClickListener((View v) -> {
                        startActivityForResult(new Intent(getApplicationContext(), VIPPersonageIntroductionActivity.class), Constants.CREATE_MEETING_VIP);
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
        if (pvTime != null && pvTime.getDialog() != null && pvTime.getDialog().isShowing()) {
            pvTime.getDialog().dismiss();
        }
        Calendar selectedDate = Calendar.getInstance();
        if (date != null) {
            selectedDate.setTime(date);
        } else {
            selectedDate.set(Calendar.HOUR_OF_DAY, 00);//时
            selectedDate.set(Calendar.MINUTE, 00);//分
            selectedDate.set(Calendar.SECOND, 00);//秒
        }
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 00, 00, 00, 00, 00);
//        if (!dataFlag && recordStartDate != null) {
//            startDate.setTime(recordStartDate);
//        } else {
//            startDate.set(Calendar.HOUR_OF_DAY, 00);//时
//            startDate.set(Calendar.MINUTE, 00);//分
//            startDate.set(Calendar.SECOND, 00);//秒
//        }
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 11, 31, 00, 00, 00);
        pvTime = new TimePickerBuilder(this, (Date selectDate, View v) -> {
            if (dataFlag) {
                if (recordEndDate == null || recordEndDate.getTime() > selectDate.getTime()) {
                    if (selectDate.getTime() < new Date().getTime()) {
                        showToast(getString(R.string.start_time_xiaoyu_current_time));
                        return;
                    }
                    recordStartDate = selectDate;
                    view.setText(TimeUtils.date2String(selectDate));
                } else {
                    showToast(getString(R.string.start_time_dayu_end_time));
                }
            } else {
                if (recordStartDate == null || selectDate.getTime() > recordStartDate.getTime()) {
                    if (selectDate.getTime() < new Date().getTime()) {
                        showToast(getString(R.string.end_time_xiaoyu_current_time));
                        return;
                    }
                    recordEndDate = selectDate;
                    view.setText(TimeUtils.date2String(selectDate));
                } else {
                    showToast(getString(R.string.start_time_dayu_end_time));
                }
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

    /**
     * 创建会议
     */
    private void createMeeting() {
        if (!isEmpty()) {
            saveBtn.setEnabled(true);
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
        if (item != null) {
            cmr.setCreateMeetingUserId(String.valueOf(item.getId()));
            cmr.setSystemId(item.getSystemId());
        }

        cmr.setMeetingName(nameView.getText().toString());
        cmr.setSponsorName(sponsorView.getText().toString());
        cmr.setMeetingAddressId(String.valueOf(addressId));
        cmr.setStartDate(TimeUtils.date2String(recordStartDate));
        cmr.setEndDate(TimeUtils.date2String(recordEndDate));
        cmr.setEstimateNum(estimatedNumberPeople.getText().toString());
        cmr.setMinNum(bottomNumberPeople.getText().toString());
        cmr.setIsImportant(String.valueOf(meetingLevelIndex == 0 ? 10 : meetingLevelIndex));
        if (receptionistListData != null && receptionistListData.size() > 1) {
            cmr.setVipReceiveUserId(getStringIds());
        }
        if (!TextUtils.isEmpty(eoUrl)) {
            cmr.setEoUrl(eoUrl);
        }
        cmr.setIsVzh(switchButton.isChecked() ? 1 : 0);
        if (vipListData != null && vipListData.size() > 1) {
            cmr.setVipList(getVipList());
        }
        networkBroker.ask(cmr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                showToast(getString(R.string.create_meeting_toast, getString(R.string.failure)));
                saveBtn.setEnabled(true);
                return;
            }
            try {
                CreateMeetingResponse response = (CreateMeetingResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        meetingId = response.data.meetingId;
                    }
                    showToast(getString(R.string.create_meeting_toast, getString(R.string.success)));
                    cemd.show();
                } else {
                    showToast(getString(R.string.create_meeting_toast, getString(R.string.failure)));
                }
            } catch (Exception e) {
                showToast(getString(R.string.create_meeting_toast, getString(R.string.failure)));
                e.printStackTrace();
            }
            saveBtn.setEnabled(true);
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
            if (cvgr.getSystemId() != 0 && !TextUtils.isEmpty(cvgr.getName())) {
                CreateMeetingRequest.Item item = new CreateMeetingRequest.Item();
                item.setSystemId(cvgr.getSystemId());
                item.setName(cvgr.getName());
                item.setIntro(cvgr.getIntro());
                listIemt.add(item);
            }
        }
        return listIemt;
    }

    /**
     * 为空验证
     *
     * @return
     */
    private boolean isEmpty() {
        if (TextUtils.isEmpty(nameView.getText())) {
            showToast(getString(R.string.please_enter_correct_, getString(R.string.meeting_name)));
            return false;
        }
        if (nameView.getText().length() < 5) {
            showToast(getString(R.string.please_enter_, getString(R.string.meeting_name)));
            return false;
        }
        if (TextUtils.isEmpty(sponsorView.getText())) {
            showToast(getString(R.string.please_enter_correct_, getString(R.string.sponsor_name)));
            return false;
        }
        if (sponsorView.getText().length() < 5) {
            showToast(getString(R.string.please_enter_, getString(R.string.sponsor_name)));
            return false;
        }
        if (addressId == -1) {
            showToast(getString(R.string.please_choose_, getString(R.string.meeting_place)));
            return false;
        }
        if (recordStartDate == null) {
            showToast(getString(R.string.please_choose_, getString(R.string.start_time)));
            return false;
        }
        if (recordEndDate == null) {
            showToast(getString(R.string.please_choose_, getString(R.string.end_time)));
            return false;
        }

        if (recordStartDate.getTime() < new Date().getTime()) {
            showToast(getString(R.string.start_time_xiaoyu_current_time));
            return false;
        }

        if (recordStartDate.getTime() > recordEndDate.getTime()) {
            showToast(getString(R.string.start_time_dayu_end_time));
            return false;
        }

        if (TextUtils.isEmpty(estimatedNumberPeople.getText())) {
            showToast(getString(R.string.please_enter_, getString(R.string.yudingrenshu)));
        }
        if (TextUtils.isEmpty(bottomNumberPeople.getText())) {
            showToast(getString(R.string.please_enter_, getString(R.string.baodingrenshu)));
        }
        return true;
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        if (filePath.indexOf("http") == 0) {
            createMeetingRequest(filePath);
        } else {
            UploadImageRequest fmm = new UploadImageRequest();
            fmm.filePath = new String[]{filePath};
            networkBroker.uploadImage(fmm, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    saveBtn.setEnabled(true);
                    return;
                }
                try {
                    UploadImageResponse response = (UploadImageResponse) res;
                    if (response.getCode() == 200 && response.data != null) {
                        UploadImageResponse.Item item = response.data;
                        if (item.dataList != null && item.dataList.size() > 0) {
                            Logger.d("上传图片返回地址：" + item.dataList.get(0));
                            createMeetingRequest(item.dataList.get(0));
                        } else {
                            saveBtn.setEnabled(true);
                        }
                    } else {
                        saveBtn.setEnabled(true);
                    }
                } catch (Exception e) {
                    saveBtn.setEnabled(true);
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Logger.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            switch (v.getId()) {
                case R.id.acmr_meeting_name:
                    if (TextUtils.isEmpty(nameView.getText())) {
                        showToast(getString(R.string.please_enter_correct_, getString(R.string.meeting_name)));
                    } else if (nameView.getText().length() < 5) {
                        showToast(getString(R.string.please_enter_, getString(R.string.meeting_name)));
                    }
                    break;
                case R.id.acmr_sponsor:
                    if (TextUtils.isEmpty(sponsorView.getText())) {
                        showToast(getString(R.string.please_enter_correct_, getString(R.string.sponsor_name)));
                    } else if (sponsorView.getText().length() < 5) {
                        showToast(getString(R.string.please_enter_, getString(R.string.sponsor_name)));
                    }
                    break;
                case R.id.acmr_estimated_number_people:
                    if (TextUtils.isEmpty(estimatedNumberPeople.getText())) {
                        showToast(getString(R.string.please_enter_correct_, getString(R.string.yudingrenshu)));
                    }
                    break;
                case R.id.acmr_bottom_number:
                    if (TextUtils.isEmpty(bottomNumberPeople.getText())) {
                        showToast(getString(R.string.please_enter_correct_, getString(R.string.baodingrenshu)));
                    }
                    break;
            }
        }
    }
}