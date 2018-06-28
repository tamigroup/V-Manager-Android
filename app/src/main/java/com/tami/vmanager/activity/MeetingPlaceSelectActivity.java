package com.tami.vmanager.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.RecycleViewDivider;
import com.tami.vmanager.base.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议地点选择
 * Created by why on 2018/6/27.
 */
public class MeetingPlaceSelectActivity extends BaseActivity {

    private Button confirm;
    private RecyclerView recyclerView;
    private List<String> listData;
    private CommonAdapter<String> commonAdapter;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_meeting_place;
    }

    @Override
    public void initView() {
        confirm = findViewById(R.id.amp_confirm);
        recyclerView = findViewById(R.id.amp_recyclerView);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        setTitleName(R.string.meeting_place);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new RecycleViewDivider(getApplicationContext(), LinearLayoutManager.HORIZONTAL,
                1, ContextCompat.getColor(getApplicationContext(), R.color.color_EAEAEA)));
        recyclerView.setLayoutManager(layoutManager);
        commonAdapter = new CommonAdapter<String>(getApplicationContext(), R.layout.item_meeting_place, getData()) {
            @Override
            protected void convert(ViewHolder holder, String str, int position) {

            }

            @Override
            public void convert(ViewHolder holder, String str) {
                holder.setText(R.id.imp_name, str);
                AppCompatImageView imageView = holder.getView(R.id.imp_selected);
                imageView.setVisibility(false ? View.VISIBLE : View.GONE);
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
    public void emptyObject() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.amp_confirm:

                break;
        }
    }

    private void Confirm(){

        setResult(1);
        finish();
    }


    private List<String> getData() {
        listData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            listData.add("地点" + i);
        }
        return listData;
    }
}
