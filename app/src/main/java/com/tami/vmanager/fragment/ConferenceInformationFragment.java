package com.tami.vmanager.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.ViewPagerBaseFragment;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdRequest;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.entity.GetMeetingResponse;
import com.tami.vmanager.enums.ServiceFlowType;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.JsonUtils;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.TimeUtils;
import com.tami.vmanager.utils.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会议信息
 * Created by why on 2018/6/16.
 */
public class ConferenceInformationFragment extends ViewPagerBaseFragment {

    private int actualNum;
    private int meetingId;
    private GetMeetingResponse.Item item;
    private TextView company_name;
    private TextView meeting_status;
    private TextView host_name;
    private TextView meeting_address;
    private TextView meeting_time;
    private TextView sale_name;
    private TextView meeting_reserve_number;
    private TextView meeting_bottom_number;
    private TextView meeting_actual_number;
    private NetworkBroker networkBroker;
    private RecyclerView recyclerView;
    private List<GetMeetingItemsByMeetingIdResponse.Array.Item> listData;
    private CommonAdapter<GetMeetingItemsByMeetingIdResponse.Array.Item> commonAdapter;

    public ConferenceInformationFragment() {
    }

    @SuppressLint("ValidFragment")
    public ConferenceInformationFragment(int meetingId, GetMeetingResponse.Item item, int actualNum) {
        this.meetingId = meetingId;
        this.item = item;
        this.actualNum = actualNum;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_conference_information;
    }

    @Override
    public void initView() {
        company_name = findViewById(R.id.company_name);
        meeting_status = findViewById(R.id.meeting_status);
        host_name = findViewById(R.id.host_name);
        meeting_address = findViewById(R.id.meeting_address);
        meeting_time = findViewById(R.id.meeting_time);
        sale_name = findViewById(R.id.sale_name);
        meeting_reserve_number = findViewById(R.id.meeting_reserve_number);
        meeting_bottom_number = findViewById(R.id.meeting_bottom_number);
        meeting_actual_number = findViewById(R.id.meeting_actual_number);
        recyclerView = findViewById(R.id.recyc);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        company_name.setText(item.meetingName);
        if(TextUtils.isEmpty(item.cancelStatus)){
            meeting_status.setVisibility(View.VISIBLE);
            meeting_status.setText(item.meetingStatus);
        }else{
            meeting_status.setVisibility(View.GONE);
        }
        host_name.setText(String.format(getResources().getString(R.string.host_name), item.sponsorName));
        meeting_address.setText(item.meetingAddress);
        StringBuilder time = new StringBuilder();
        String startTime = TimeUtils.milliseconds2String(item.startTime,TimeUtils.DATE_MMDDHHMM_SLASH);
        time.append(startTime);
        time.append(" - ");
        String endTime = TimeUtils.milliseconds2String(item.endTime,TimeUtils.DATE_MMDDHHMM_SLASH);
        time.append(endTime);
        meeting_time.setText(time.toString());
        sale_name.setText(String.format(getResources().getString(R.string.salename), item.saleUserName));
        meeting_reserve_number.setText(String.format(getResources().getString(R.string.predetermined_number), String.valueOf(item.estimateNum)));
        meeting_bottom_number.setText(String.format(getResources().getString(R.string.bottom_number), String.valueOf(item.minNum)));

        //V智慧判断
        if (item.isVzh == 1) {
//            meeting_actual_number.setText(String.format(getResources().getString(R.string.actual_number), String.valueOf(item.actualNum)));
            meeting_actual_number.setText(String.format(getResources().getString(R.string.actual_number), String.valueOf(actualNum)));
        } else {
            meeting_actual_number.setText(String.format(getResources().getString(R.string.actual_number), "--"));
        }


        listData = new ArrayList<>();
        initRecyc();
    }

    private void initRecyc() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commonAdapter = new CommonAdapter<GetMeetingItemsByMeetingIdResponse.Array.Item>(getContext(), R.layout.item_fragment_conference_information, listData) {

            @Override
            protected void convert(ViewHolder holder, GetMeetingItemsByMeetingIdResponse.Array.Item item, int position) {
                TextView item_time = holder.getView(R.id.item_time);
                TextView item_name = holder.getView(R.id.item_name);
                TextView item_sure = holder.getView(R.id.item_sure);
                TextView item_day = holder.getView(R.id.item_day);
                if(item.startOn!=null&&item.startOn!=0){
                    item_time.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH));
                    item_day.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_MMDD_SLASH));
                }
                item_name.setText(item.meetingItemName);
                item_sure.setText(getResources().getString(R.string.no_confirmed));
                if (item.selectStatus == ServiceFlowType.CONFIRMED.getType()) {
                    set_text(item_time, R.color.color_333333, R.mipmap.time, true);
                    item_sure.setText(getResources().getString(R.string.confirmed));
                    set_text(item_sure, R.color.color_34DB8E, R.mipmap.icon_ok, false);
                    item_name.setTextColor(getResources().getColor(R.color.color_333333));
                    item_day.setTextColor(getResources().getColor(R.color.color_333333));
                }else if (item.selectStatus == ServiceFlowType.RECONFIRMED.getType()){
                    set_text(item_time, R.color.color_ACACAC, R.mipmap.time_gray, true);
                    item_sure.setText(getResources().getString(R.string.re_confirmed));
                    set_text(item_sure, R.color.color_ACACAC, R.mipmap.middle_pic_problem, false);
                    item_name.setTextColor(getResources().getColor(R.color.color_ACACAC));
                    item_day.setTextColor(getResources().getColor(R.color.color_ACACAC));
                }
            }
        };
        recyclerView.setAdapter(commonAdapter);
    }

    /**
     * 会议状态改变 设置text
     */
    private void set_text(TextView text, int color, int draw, boolean left_right) {
        text.setTextColor(getResources().getColor(color));
        Drawable drawable = getResources().getDrawable(draw);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (left_right) {
            text.setCompoundDrawables(drawable, null, null, null);
        } else {
            text.setCompoundDrawables(null, null, drawable, null);
        }

    }

    @Override
    public void requestNetwork() {
        networkBroker = new NetworkBroker(getActivity());
        GetMeetingItemsByMeetingIdRequest getMeetingItemsByMeetingIdRequest = new GetMeetingItemsByMeetingIdRequest();
        getMeetingItemsByMeetingIdRequest.setMeetingId(meetingId);
        //        getMeetingItemsByMeetingIdRequest.setMeetingId(1);
        networkBroker.ask(getMeetingItemsByMeetingIdRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.e(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetMeetingItemsByMeetingIdResponse response = (GetMeetingItemsByMeetingIdResponse) res;
                if (response.getCode() == 200) {
                    GetMeetingItemsByMeetingIdResponse.Array array = response.data;
                    if (array != null && array.dataList != null && array.dataList.size() > 0) {
                        listData.addAll(array.dataList);
                        commonAdapter.notifyDataSetChanged();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }
}
