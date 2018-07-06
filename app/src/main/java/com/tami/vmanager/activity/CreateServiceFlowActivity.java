package com.tami.vmanager.activity;

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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.dialog.ServiceFlowDialog;
import com.tami.vmanager.entity.GetMeetingItemsRequest;
import com.tami.vmanager.entity.GetMeetingItemsResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.view.MeetingStateView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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
    }

    @Override
    public void initListener() {
        dateSelected.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        addView.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String day = null;
        Intent intent = getIntent();
        if (intent != null) {
            day = intent.getStringExtra(Constants.KEY_DAY);
        }
        setTitleName(R.string.flow_chart);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        initRecyclerView();

        serviceFlowDialog = new ServiceFlowDialog(this);
    }

    @Override
    public void requestNetwork() {
        getMeetingItems();
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
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = null;
        networkBroker.cancelAllRequests();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.acsf_date_selected:
                showPopWindow(dateSelected);
                break;
            case R.id.acsf_save_btn:
                //保存
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
                        showCustomRole(customRole);
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

                if (item.isSelected) {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                    selectedView.setImageResource(R.mipmap.people_checkbox_selected);
                } else {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                    selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                }
                //如果为选中状态则设置时间可点击
                timeView.setOnClickListener((View view) -> {
                    createTime(timeView, position, true);
                });
                timeView.setEnabled(item.isSelected ? true : false);

                //时间不为空时添充
                if (item.createOn != 0) {
                    timeView.setText(TimeUtils.date2String(new Date(item.createOn), TimeUtils.DATE_HHMM_SLASH));
                    timeView.setEnabled(true);
                }

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
                            bottomData.add(topData.get(position));
                            bottomAdapter.notifyDataSetChanged();
                            topData.remove(position);
                            topAdapter.notifyDataSetChanged();
                            bottomRecyclerView.setVisibility(View.VISIBLE);
                            return;
                        }
                        selectedView.setImageResource(R.mipmap.people_checkbox_unselected);
                        timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_888888));
                        timeView.setEnabled(false);
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
                    showCustomRole(customRole);
                });
                if (item.isSelected) {
                    //勾选的时间颜色会变
                    timeView.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
                    customRole.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.color_3B89E9));
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

    /**
     * 弹出时间框
     *
     * @param view 点击哪个VIEW
     */
    private void createTime(TextView view, int position, boolean flag) {
        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2020, 11, 31);
        TimePickerView pvTime = new TimePickerBuilder(this, (Date date, View v) -> {
            if (flag) {
                topData.get(position).createOn = date.getTime();
                Collections.sort(topData);
                topAdapter.notifyDataSetChanged();
            } else {
                //追加数据到TOPListView中
                GetMeetingItemsResponse.Array.Item item = bottomData.get(position);
                item.createOn = date.getTime();
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
    private void getMeetingItems() {
        GetMeetingItemsRequest gmir = new GetMeetingItemsRequest();
        gmir.setMeetingId(String.valueOf(29));
        gmir.setStartDate("2018-07-10");
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
                            topData.addAll(array.dataList);
                            Collections.sort(topData);
                            topAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private RecyclerView roleRecyclerView;
    private List<String> roleData = new ArrayList<>();
    private CommonAdapter<String> croleAdapter;
    private int selectIndex = -1;

    public void showCustomRole(TextView textView) {
        mBottomSheetDialog = new BottomSheetDialog(this);
        ConstraintLayout cLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.show_menu_service_flow, null);
        TextView cancle = cLayout.findViewById(R.id.smsf_cancel);
        TextView confirm = cLayout.findViewById(R.id.smsf_confirm);
        cancle.setOnClickListener((View v) -> {
            mBottomSheetDialog.dismiss();
        });
        confirm.setOnClickListener((View v) -> {
            mBottomSheetDialog.dismiss();
        });
        setData1();
        roleRecyclerView = cLayout.findViewById(R.id.smsf_recyclerview);
        GridLayoutManager layoutManage = new GridLayoutManager(getApplicationContext(), 3);
        roleRecyclerView.setLayoutManager(layoutManage);
        croleAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.item_role, roleData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                MeetingStateView roleView = holder.getView(R.id.item_role_txt);
                roleView.setTextStyle(s, R.color.color_333333, android.R.color.black, 1, 5);
                roleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectIndex == -1) {
                            roleView.setTextStyle(s, R.color.color_3B89E9, R.color.color_3B89E9, 1, 5);
                        } else {
                            MeetingStateView selectView = roleRecyclerView.getChildAt(selectIndex).findViewById(R.id.item_role_txt);
                            selectView.setTextStyle(selectView.getText().toString(), R.color.color_333333, android.R.color.black, 1, 5);
                            roleView.setTextStyle(s, R.color.color_3B89E9, R.color.color_3B89E9, 1, 5);
                        }
                        textView.setText(s);
                        selectIndex = position;
                    }
                });
            }
        };
        roleRecyclerView.setAdapter(croleAdapter);
        mBottomSheetDialog.setContentView(cLayout);
        mBottomSheetDialog.show();
    }

    private void setData1() {
        for (int i = 0; i < 10; i++) {
            roleData.add("name" + i);
        }
    }

    private PopupWindow popupWindow;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showPopWindow(TextView view) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.show_menu_date, null);
        RecyclerView recyclerView = view1.findViewById(R.id.smd_recyclerview);
        List<String> str = new ArrayList<>();
        str.add("测试一");
        str.add("测试二");
        str.add("测试三");

        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.show_menu_date_item, str) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView dataView = holder.getView(R.id.menu_item_data);
                dataView.setText(s);
                dataView.setOnClickListener((View v) -> {
                    popupWindow.dismiss();
                    view.setText(dataView.getText().toString());
                });
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(commonAdapter);
        popupWindow = new PopupWindow(recyclerView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        ColorDrawable cd = new ColorDrawable(0x00ffffff);// 背景颜色全透明
        popupWindow.setBackgroundDrawable(cd);
        popupWindow.setAnimationStyle(R.style.style_pop_animation);// 动画效果必须放在showAsDropDown()方法上边，否则无效
        popupWindow.showAsDropDown(view, -ScreenUtil.dip2px(getApplicationContext(), 50), 0, Gravity.CENTER);
    }


}