package com.tami.vmanager.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;
import com.tami.vmanager.view.SwitchButton;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议环节待确认
 * Created by why on 2018/6/15.
 */

public class MeetingLinkConfirmedActivity extends BaseActivity {

    private TextView content;
    private RecyclerView recyclerView;
    private TextView addPerson;
    private SwitchButton switchbtn;
    private TextView important;
    private Button confirmBtn;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_link_confirmed;
    }

    @Override
    public void initView() {
        content = findViewById(R.id.confirmation_content_txt);
        recyclerView = findViewById(R.id.meeting_link_confirmed_listview);
        addPerson = findViewById(R.id.mlc_add_the_person_in_charge);
        switchbtn = findViewById(R.id.mlc_switchbtn);
        important = findViewById(R.id.mlc_important);
        confirmBtn = findViewById(R.id.mlc_confirm_btn);
    }

    @Override
    public void initListener() {
        switchbtn.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
                Toast.makeText(getApplicationContext(), String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });
        addPerson.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_link_confirmed);

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.percentage_10)));
        listData = getData();
        commonAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.item_meeting_link_confirmed, listData) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }

            @Override
            public void convert(ViewHolder holder, String s) {
                //赋值
            }
        };
        recyclerView.setAdapter(commonAdapter);

    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mlc_add_the_person_in_charge:
                if (recyclerView.getMeasuredHeight() > ScreenUtil.dip2px(getApplicationContext(), 320)) {
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
                    layoutParams.height = ScreenUtil.dip2px(getApplicationContext(), 320);
                }
                listData.add("测试数据");
                commonAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void emptyObject() {
        switchbtn = null;
        if (recyclerView != null) {
            recyclerView.removeAllViews();
            recyclerView = null;
        }
    }

    List<String> listData;
    CommonAdapter commonAdapter;

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            data.add("测试数据" + i);
        }
        return data;
    }
}
