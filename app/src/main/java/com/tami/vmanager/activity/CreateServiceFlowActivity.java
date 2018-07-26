package com.tami.vmanager.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.ConfirmEnterMeetingDialog;
import com.tami.vmanager.dialog.ConfirmEnterMeetingListener;
import com.tami.vmanager.dialog.ServiceFlowDialog;
import com.tami.vmanager.entity.CreateUserMeetingItemRequest;
import com.tami.vmanager.entity.CreateUserMeetingItemResponse;
import com.tami.vmanager.entity.GetMeetingDayRequest;
import com.tami.vmanager.entity.GetMeetingDayResponse;
import com.tami.vmanager.entity.GetMeetingItemsRequest;
import com.tami.vmanager.entity.GetMeetingItemsResponse;
import com.tami.vmanager.entity.MeetingItemsJsonEntity;
import com.tami.vmanager.entity.SetMeetingItemsRequest;
import com.tami.vmanager.entity.SetMeetingItemsResponse;
import com.tami.vmanager.entity.SystemRoleListRequest;
import com.tami.vmanager.entity.SystemRoleListResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 创建服务流程
 * Created by why on 2018/6/26.
 */
public class CreateServiceFlowActivity extends BaseActivity {

    private TextView dateSelected;//日期
    private AppCompatImageView dateSelectedImage;//多天的时候显示

    private RecyclerView topRecyclerView;//服务器固定项
    private RecyclerView bottomRecyclerView;//追加自定义项
    private CommonAdapter<GetMeetingItemsResponse.Array.Item> topAdapter;
    private CommonAdapter<GetMeetingItemsResponse.Array.Item> bottomAdapter;
    private List<GetMeetingItemsResponse.Array.Item> topData;
    private List<GetMeetingItemsResponse.Array.Item> bottomData;
    private TextView addView;//添加按钮
    private Button saveBtn;//保存按钮
    private NetworkBroker networkBroker;
    private ServiceFlowDialog serviceFlowDialog;//创建流程
    private BottomSheetDialog mBottomSheetDialog;//选择角色

    //日期选择框
    private PopupWindow popupWindow;
    private List<GetMeetingDayResponse.Array.Item> meetingDayList;
    private CommonAdapter<GetMeetingDayResponse.Array.Item> meetingDayAdapter;
    //选择角色
    private RecyclerView roleRecyclerView;
    private List<SystemRoleListResponse.Item> roleData;
    private CommonAdapter<SystemRoleListResponse.Item> roleAdapter;
    private int selectIndex = -1;
    //保存弹框
    private ConfirmEnterMeetingDialog cemd;

    private int meetingId = -1;//会议ID

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_create_service_flow;
    }

    @Override
    public void initView() {
        dateSelected = findViewById(R.id.acsf_date_selected);
        dateSelectedImage = findViewById(R.id.acsf_date_selected_image);

        topRecyclerView = findViewById(R.id.acsf_top_recyclerview);
        bottomRecyclerView = findViewById(R.id.acsf_bottom_recyclerview);
        addView = findViewById(R.id.fcsf_add_process);
        saveBtn = findViewById(R.id.acsf_save_btn);

        serviceFlowDialog = new ServiceFlowDialog(this);
        cemd = new ConfirmEnterMeetingDialog(this);
        cemd.setContentRes(R.string.do_you_save_current_process);
    }

    @Override
    public void initListener() {
        dateSelected.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        addView.setOnClickListener(this);

        cemd.setConfirmEnterMeetingListener(new ConfirmEnterMeetingListener() {
            @Override
            public void leftBtn() {
                showPopWindow(dateSelected);
            }

            @Override
            public void rightBtn() {
                createUserMeetingItem(true);
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, -1);
            if (meetingId == -1) {
                showToast(getString(R.string.meeting_not_exist_please_recreate));
                finish();
            }
        }

        setTitleName(R.string.flow_chart);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        initRecyclerView();

        //日期
        meetingDayList = new ArrayList<>();
        //角色
        roleData = new ArrayList<>();
    }

    @Override
    public void requestNetwork() {
        getSystemRoleList();
    }

    @Override
    public void removeListener() {
        serviceFlowDialog.removeListener();
    }

    @Override
    public void emptyObject() {
        if (serviceFlowDialog != null && serviceFlowDialog.isShowing()) {
            serviceFlowDialog.dismiss();
        }
        serviceFlowDialog = null;
        if (mBottomSheetDialog != null && mBottomSheetDialog.isShowing()) {
            mBottomSheetDialog.dismiss();
        }
        mBottomSheetDialog = null;
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            } else {
                popupWindow.setContentView(null);
            }
        }
        popupWindow = null;
        if (cemd != null && cemd.isShowing()) {
            cemd.dismiss();
        }
        cemd = null;
        if (topData != null) {
            topData.clear();
        }
        if (bottomData != null) {
            bottomData.clear();
        }
        if (meetingDayList != null) {
            meetingDayList.clear();
        }
        if (roleData != null) {
            roleData.clear();
        }
        dateSelectedImage.setBackgroundResource(0);
        dateSelectedImage = null;
        networkBroker.cancelAllRequests();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.acsf_date_selected:
                if (meetingDayList != null && meetingDayList.size() > 1) {
                    cemd.show();
                }
                break;
            case R.id.acsf_save_btn:
                //保存
                saveBtn.setEnabled(false);
                createUserMeetingItem(false);
                break;
            case R.id.fcsf_add_process:
                //添加自定义
                serviceFlowDialog.setServiceFlowListener((String name) -> {
                    Logger.d("创建流程名称：" + name);
                    addCustomProcess(name);
                });
                serviceFlowDialog.show();
                break;
        }
    }

    /**
     * 初始化列表
     */
    private void initRecyclerView() {
        LinearLayoutManager topManager = new LinearLayoutManager(getApplicationContext());
        topManager.setOrientation(LinearLayoutManager.VERTICAL);
        topRecyclerView.setLayoutManager(topManager);
        LinearLayoutManager bottomManager = new LinearLayoutManager(getApplicationContext());
        bottomManager.setOrientation(LinearLayoutManager.VERTICAL);
        bottomRecyclerView.setLayoutManager(bottomManager);

        topData = new ArrayList<>();
        topAdapter = new CommonAdapter<GetMeetingItemsResponse.Array.Item>(getApplicationContext(), R.layout.item_create_service_flow, topData) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingItemsResponse.Array.Item item, int position) {
                //角色
                TextView customRole = holder.getView(R.id.icsf_custom_role);
                //删除组隐藏显示
                Group group = holder.getView(R.id.icsf_group);
                if (item.isCustom) {
                    group.setVisibility(View.VISIBLE);
                    holder.getView(R.id.icsf_editing_time).setVisibility(View.GONE);
                    customRole.setOnClickListener((View v) -> {
                        showCustomRole(customRole, item);
                    });
                    if (item.isSelected) {
                        customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                        if (item.role != null && !TextUtils.isEmpty(item.role.name)) {
                            //角色不为空时添加角色名称
                            customRole.setText(item.role.name);
                        }
                        customRole.setEnabled(true);
                    } else {
                        customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                        customRole.setText(null);
                        customRole.setEnabled(false);
                    }
                    //删除图片
                    AppCompatImageView deleteView = holder.getView(R.id.icsf_delete);
                    deleteView.setOnClickListener((View view) -> {
                        topData.remove(position);
                        notifyDataSetChanged();
                    });
                } else {
                    group.setVisibility(View.GONE);
                    holder.getView(R.id.icsf_editing_time).setVisibility(View.VISIBLE);
                }
                //流程名称
                holder.setText(R.id.icsf_name, item.name);
                //编辑时间
                TextView timeView = holder.getView(item.isCustom ? R.id.icsf_custom_editing_time : R.id.icsf_editing_time);
                //选中图片
                AppCompatImageView selectedView = holder.getView(R.id.icsf_selected);

                //时间不为空时添充
                if (item.startOn != 0) {
                    timeView.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH));
                    item.isSelected = true;
                    timeView.setEnabled(true);
                }

                if (item.isSelected) {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                    selectedView.setImageResource(R.mipmap.people_checkbox_selected);
                } else {
                    //勾选的时间颜色会变
                    item.startOn = 0;
                    timeView.setText(null);
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                    selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                }
                //如果为选中状态则设置时间可点击
                timeView.setOnClickListener((View view) -> {
                    createTime(timeView, position, true);
                });
                timeView.setEnabled(item.isSelected ? true : false);

                selectedView.setOnClickListener((View view) -> {
                    item.isSelected = !item.isSelected;
                    if (item.isSelected) {
                        selectedView.setImageResource(R.mipmap.people_checkbox_selected);
                        timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                        timeView.setEnabled(true);
                        if (item.isCustom) {
                            customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                            customRole.setEnabled(true);
                        }
                    } else {
                        if (item.isCustom) {
                            customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                            customRole.setEnabled(false);
                            GetMeetingItemsResponse.Array.Item removeItem = topData.get(position);
                            if (removeItem.role != null) {
                                removeItem.role.name = null;
                            }
                            removeItem.startOn = 0;
                            bottomData.add(removeItem);
                            bottomAdapter.notifyDataSetChanged();
                            topData.remove(position);
                            topAdapter.notifyDataSetChanged();
                            bottomRecyclerView.setVisibility(View.VISIBLE);
                            return;
                        }
                        selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                        item.startOn = 0;
                        timeView.setText(null);
                        timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                        timeView.setEnabled(false);
                        Collections.sort(topData);
                        topAdapter.notifyDataSetChanged();
                    }
                });

            }
        };
        topRecyclerView.setAdapter(topAdapter);

        bottomData = new ArrayList<>();
        bottomAdapter = new CommonAdapter<GetMeetingItemsResponse.Array.Item>(getApplicationContext(), R.layout.item_create_service_flow, bottomData) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingItemsResponse.Array.Item item, int position) {
                //删除组隐藏显示
                Group group = holder.getView(R.id.icsf_group);
                group.setVisibility(View.VISIBLE);
                //隐藏默认的编辑时间
                holder.getView(R.id.icsf_editing_time).setVisibility(View.GONE);
                //流程名称
                holder.setText(R.id.icsf_name, item.name);
                //编辑时间
                TextView timeView = holder.getView(R.id.icsf_custom_editing_time);
                //角色
                TextView customRole = holder.getView(R.id.icsf_custom_role);
                //选中图片
                AppCompatImageView selectedView = holder.getView(R.id.icsf_selected);
                //删除图片
                AppCompatImageView deleteView = holder.getView(R.id.icsf_delete);
                //勾选的时间可点击
                timeView.setOnClickListener((View view) -> {
                    createTime(timeView, position, false);
                });
                //角色选择
                customRole.setOnClickListener((View v) -> {
                    showCustomRole(customRole, item);
                });
                if (item.isSelected) {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                    customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                    if (item.startOn != 0) {
                        timeView.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH));
                    }
                    if (item.role != null && !TextUtils.isEmpty(item.role.name)) {
                        customRole.setText(item.role.name);
                    }
                    selectedView.setImageResource(R.mipmap.people_checkbox_selected);
                    timeView.setEnabled(true);
                    customRole.setEnabled(true);
                } else {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                    customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                    //清空显示默认值
                    customRole.setText(null);
                    timeView.setText(null);
                    selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                    timeView.setEnabled(false);
                    customRole.setEnabled(false);
                }
                //选中
                selectedView.setOnClickListener((View view) -> {
                    item.isSelected = !item.isSelected;
                    if (item.isSelected) {
                        selectedView.setImageResource(R.mipmap.people_checkbox_selected);
                        customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                        timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                        timeView.setEnabled(true);
                        customRole.setEnabled(true);
                    } else {
                        selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                        customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                        timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                        timeView.setEnabled(false);
                        customRole.setEnabled(false);
                    }
                });
                //删除
                deleteView.setOnClickListener((View view) -> {
                    bottomData.remove(position);
                    notifyDataSetChanged();
                });
            }
        };
        bottomRecyclerView.setAdapter(bottomAdapter);
    }

    private TimePickerView pvTime;

    /**
     * 弹出时间框
     *
     * @param view 点击哪个VIEW
     */
    private void createTime(TextView view, int position, boolean flag) {
        if (pvTime != null && pvTime.getDialog() != null && pvTime.getDialog().isShowing()) {
            pvTime.getDialog().dismiss();
        }
        Calendar selectedDate = Calendar.getInstance();
        if (!TextUtils.isEmpty(dateSelected.getText())) {
            Date selectData = TimeUtils.string2Date(dateSelected.getText().toString(), TimeUtils.DATE_YYYYMMDD_SLASH);
            selectedDate.setTime(selectData);
        }
        pvTime = new TimePickerBuilder(this, (Date date, View v) -> {
            if (flag) {
                topData.get(position).startOn = date.getTime();
                Collections.sort(topData);
                topAdapter.notifyDataSetChanged();
            } else {
                //追加数据到TOPListView中
                GetMeetingItemsResponse.Array.Item item = bottomData.get(position);
                item.startOn = date.getTime();
                //只有时间和角色都选择后才向TOP追加
                if (item.role != null && !TextUtils.isEmpty(item.role.name)) {
                    topData.add(item);
                    Collections.sort(topData);
                    topAdapter.notifyDataSetChanged();
                    //底部列表移除数据
                    bottomData.remove(position);
                    //没有数据时隐藏一下
                    if (bottomData.size() > 0) {
                        Collections.sort(bottomData);
                        bottomAdapter.notifyDataSetChanged();
                    } else {
                        bottomRecyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Collections.sort(bottomData);
                    bottomAdapter.notifyDataSetChanged();
                }
            }
        }).setType(new boolean[]{false, false, false, true, true, false})
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
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
     * 添加自定义流程
     *
     * @param name
     */
    private void addCustomProcess(String name) {
        GetMeetingItemsResponse.Array.Item item = new GetMeetingItemsResponse.Array.Item();
        item.name = name;
        item.isCustom = true;
        bottomData.add(item);
        bottomAdapter.notifyDataSetChanged();
        //有新布局时要显示一下
        bottomRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取服务器全部节点
     */
    private void getMeetingItems(String day) {
        GetMeetingItemsRequest gmir = new GetMeetingItemsRequest();
        gmir.setMeetingId(meetingId);
        gmir.setStartDate(day);
        networkBroker.ask(gmir, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemsResponse response = (GetMeetingItemsResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        GetMeetingItemsResponse.Array array = response.data;
                        if (array != null && array.dataList != null && array.dataList.size() > 0) {
                            if (topData != null && topData.size() > 0) {
                                topData.clear();
                            }
                            for (GetMeetingItemsResponse.Array.Item item : array.dataList) {
                                if (item.systemId != 0) {
                                    for (SystemRoleListResponse.Item roleItem : roleData) {
                                        if (item.roleId == roleItem.id) {
                                            item.isCustom = true;
                                            if (item.role == null) {
                                                item.role = new GetMeetingItemsResponse.Array.Item.Role();
                                            }
                                            item.role.id = roleItem.id;
                                            item.role.name = roleItem.roleName;
                                            if (item.startOn == 0) {
                                                item.isSelected = false;
                                                bottomData.add(item);
                                            } else {
                                                item.isSelected = true;
                                                topData.add(item);
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    topData.add(item);
                                }
                            }
                            Collections.sort(topData);
                            topAdapter.notifyDataSetChanged();
                            if (bottomData != null && bottomData.size() > 0) {
                                Collections.sort(bottomData);
                                bottomAdapter.notifyDataSetChanged();
                                bottomRecyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 选择角色
     *
     * @param textView
     */
    public void showCustomRole(TextView textView, GetMeetingItemsResponse.Array.Item item) {
        mBottomSheetDialog = new BottomSheetDialog(this);
        ConstraintLayout cLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.show_menu_service_flow, null);
        TextView cancle = cLayout.findViewById(R.id.smsf_cancel);
        TextView confirm = cLayout.findViewById(R.id.smsf_confirm);
        cancle.setOnClickListener((View v) -> {
            mBottomSheetDialog.dismiss();
        });
        confirm.setOnClickListener((View v) -> {
            mBottomSheetDialog.dismiss();
            if (selectIndex != -1) {
                createUserMeetingItem(item);
            }
        });
        roleRecyclerView = cLayout.findViewById(R.id.smsf_recyclerview);
        GridLayoutManager layoutManage = new GridLayoutManager(getApplicationContext(), 3);
        roleRecyclerView.setLayoutManager(layoutManage);
        roleAdapter = new CommonAdapter<SystemRoleListResponse.Item>(getApplicationContext(), R.layout.item_role, roleData) {
            @Override
            protected void convert(ViewHolder holder, SystemRoleListResponse.Item roleItem, int position) {
                MeetingStateView roleView = holder.getView(R.id.item_role_txt);
                roleView.setTextStyle(roleItem.roleName, R.color.color_333333, android.R.color.black, 1, 5);
                roleView.setOnClickListener((View v) -> {
                    if (selectIndex == -1) {
                        roleView.setTextStyle(roleItem.roleName, R.color.color_3B89E9, R.color.color_3B89E9, 1, 5);
                    } else {
                        MeetingStateView selectView = roleRecyclerView.getChildAt(selectIndex).findViewById(R.id.item_role_txt);
                        selectView.setTextStyle(selectView.getText().toString(), R.color.color_333333, android.R.color.black, 1, 5);
                        roleView.setTextStyle(roleItem.roleName, R.color.color_3B89E9, R.color.color_3B89E9, 1, 5);
                    }
                    textView.setText(roleItem.roleName);
                    if (item.role == null) {
                        item.role = new GetMeetingItemsResponse.Array.Item.Role();
                    }
                    item.role.name = roleItem.roleName;
                    selectIndex = position;
                });
            }
        };
        roleRecyclerView.setAdapter(roleAdapter);
        mBottomSheetDialog.setContentView(cLayout);
        mBottomSheetDialog.show();
    }

    @SuppressLint("NewApi")
    private void showPopWindow(TextView view) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view1 = layoutInflater.inflate(R.layout.show_menu_date, null);
        RecyclerView recyclerView = view1.findViewById(R.id.smd_recyclerview);
        meetingDayAdapter = new CommonAdapter<GetMeetingDayResponse.Array.Item>(getApplicationContext(), R.layout.show_menu_date_item, meetingDayList) {
            @Override
            protected void convert(ViewHolder holder, GetMeetingDayResponse.Array.Item item, int position) {
                TextView dataView = holder.getView(R.id.menu_item_data);
                dataView.setText(item.day);
                dataView.setOnClickListener((View v) -> {
                    popupWindow.dismiss();
                    String dataStr = dataView.getText().toString();
                    view.setText(dataStr);
                    getMeetingItems(dataStr);
                    if (bottomData != null) {
                        bottomData.clear();
                    }
                    bottomAdapter.notifyDataSetChanged();
                    bottomRecyclerView.setVisibility(View.GONE);
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(meetingDayAdapter);
        popupWindow = new PopupWindow(recyclerView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x00ffffff);// 背景颜色全透明
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);// 动画效果必须放在showAsDropDown()方法上边，否则无效
        popupWindow.showAsDropDown(view, -ScreenUtil.dip2px(getApplicationContext(), 60), 0, Gravity.CENTER);
    }

    /**
     * 获取会议天数
     */
    public void getMeetingDays() {
        GetMeetingDayRequest gmdr = new GetMeetingDayRequest();
        gmdr.setMeetingId(meetingId);
        networkBroker.ask(gmdr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingDayResponse response = (GetMeetingDayResponse) res;
                if (response.getCode() == 200) {
                    GetMeetingDayResponse.Array array = response.data;
                    if (array != null && array.dataList != null && array.dataList.size() > 0) {
                        meetingDayList.addAll(array.dataList);
                        dateSelected.setText(meetingDayList.get(0).day);
                        if (meetingDayList.size() > 1) {
                            dateSelectedImage.setVisibility(View.VISIBLE);
                        } else {
                            dateSelectedImage.setVisibility(View.GONE);
                        }
                        getMeetingItems(meetingDayList.get(0).day);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 获取角色数据
     */
    public void getSystemRoleList() {
        SystemRoleListRequest srlr = new SystemRoleListRequest();
        networkBroker.ask(srlr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SystemRoleListResponse response = (SystemRoleListResponse) res;
                if (response.getCode() == 200) {
                    List<SystemRoleListResponse.Item> list = response.data;
                    if (list != null && list.size() > 0) {
                        roleData.addAll(list);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            getMeetingDays();
        });
    }

    /**
     * 创建自定义流程节点
     */
    private void createUserMeetingItem(GetMeetingItemsResponse.Array.Item item) {
        CreateUserMeetingItemRequest cumir = new CreateUserMeetingItemRequest();
        cumir.setMeetingId(meetingId);
        cumir.setMeetingItemId(item.id);
        if (!TextUtils.isEmpty(item.name)) {
            cumir.setMeetingItemName(item.name);
        }
        cumir.setRoleId(roleData.get(selectIndex).id);
        networkBroker.ask(cumir, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                CreateUserMeetingItemResponse response = (CreateUserMeetingItemResponse) res;
                if (response.getCode() == 200) {
                    CreateUserMeetingItemResponse.Item cItem = response.data;
                    if (cItem != null) {
                        item.id = cItem.id;
                        //只有时间和角色都选择后才向TOP追加
                        if (item.startOn != 0) {
                            topData.add(item);
                            Collections.sort(topData);
                            topAdapter.notifyDataSetChanged();
                            //底部列表移除数据
                            bottomData.remove(item);
                            //没有数据时隐藏一下
                            if (bottomData.size() > 0) {
                                Collections.sort(bottomData);
                                bottomAdapter.notifyDataSetChanged();
                            } else {
                                bottomRecyclerView.setVisibility(View.GONE);
                            }
                        } else {
                            Collections.sort(bottomData);
                            bottomAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 保存流程节点
     */
    private void createUserMeetingItem(final boolean flag) {
        if (!isEmpty()) {
            saveBtn.setEnabled(true);
            return;
        }
        SetMeetingItemsRequest smir = new SetMeetingItemsRequest();
        smir.setMeetingId(meetingId);
        if (!TextUtils.isEmpty(dateSelected.getText())) {
            smir.setStartOn(dateSelected.getText().toString());
        }
        smir.setMeetingItemsJson(getMeetingJson());
        if (!TextUtils.isEmpty(smir.getMeetingItemsJson())) {
            networkBroker.ask(smir, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    saveBtn.setEnabled(true);
                    return;
                }
                try {
                    SetMeetingItemsResponse response = (SetMeetingItemsResponse) res;
                    if (response.getCode() == 200) {
                        if (response.data) {
                            showToast(getString(R.string.save_flow, getString(R.string.success)));
                            if (flag) {
                                showPopWindow(dateSelected);
                            } else {
                                setResult(Constants.CREATE_FLOW);
                                finish();
                            }
                        } else {
                            showToast(getString(R.string.save_flow, getString(R.string.failure)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                saveBtn.setEnabled(true);
            });
        } else {
            saveBtn.setEnabled(true);
            showToast(getString(R.string.select_process_node_least));
        }
    }

    /**
     * 返回JSON串
     *
     * @return
     */
    private String getMeetingJson() {
        List<MeetingItemsJsonEntity> jsonList = new ArrayList<>();
        for (GetMeetingItemsResponse.Array.Item item : topData) {
            if (item.isSelected && item.startOn > 0) {
                MeetingItemsJsonEntity mie = new MeetingItemsJsonEntity();
                mie.meetingItemId = item.id;
                mie.startOn = item.startOn;
                jsonList.add(mie);
            }
        }
        String json = null;
        if (jsonList.size() > 0) {
            json = new Gson().toJson(jsonList);
        }
        return json;
    }

    /**
     * 验证页面数据
     *
     * @return
     */
    private boolean isEmpty() {
        for (GetMeetingItemsResponse.Array.Item item : topData) {
            if (item.isSelected && item.startOn == 0) {
                showToast(getString(R.string.please_choose_node_time, item.name));
                return false;
            }
        }
        return true;
    }
}