package com.tami.vmanager.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.view.indexlib.CarBean;
import com.tami.vmanager.view.indexlib.IndexLayout;
import com.tami.vmanager.view.indexlib.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * 人员名单
 * Created by why on 2018/7/5.
 */
public class PersonnelListActivity extends BaseActivity {


    private RecyclerView mRecyclerView;
    private CommonAdapter<CarBean.CarInfo> commonAdapter;
    private IndexLayout indexLayout;
    private List<String> headerName = new ArrayList<>();
    private TextView selectTv;
    private TextView search_name;

    @Override
    public int getContentViewId() {
        return R.layout.activity_personnel_list;
    }

    @Override
    public void initView() {
        selectTv = findViewById(R.id.selectTv);
        search_name = findViewById(R.id.search_name);
        mRecyclerView = findViewById(R.id.recyc);
        indexLayout = findViewById(R.id.indexBar);

        CarBean carBean = new Utils().readFromAssets(PersonnelListActivity.this);
        final List<CarBean.CarInfo> carList = carBean.getData();
        final NormalDecoration decoration = new NormalDecoration() {
            @Override
            public String getHeaderName(int pos) {
                return carList.get(pos).getInitial();
            }
        };
        decoration.setHeaderHeight(com.tami.vmanager.utils.Utils.dp2px(this,25));
        final LinearLayoutManager manager = new LinearLayoutManager(this);

        commonAdapter = new CommonAdapter<CarBean.CarInfo>(this, R.layout.item_rec_car, carList) {

            @Override
            protected void convert(ViewHolder holder, CarBean.CarInfo carInfo, int position) {
                ImageView item_iv = holder.getView(R.id.item_iv);
                Picasso.get().load(carInfo.getLogoUrl()).into(item_iv);
                holder.setText(R.id.item_tv, carInfo.getName());
            }
        };
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(commonAdapter);


        List<String> heads = new ArrayList<>();
        for (CarBean.CarInfo car : carList) {
            if (!heads.contains(car.getInitial())) {
                heads.add(car.getInitial());
            }
        }
        indexLayout.setIndexBarHeightRatio(0.9f);
        indexLayout.getIndexBar().setIndexsList(heads);
        indexLayout.getIndexBar().setIndexChangeListener(indexName -> {
            for (int i = 0; i < carList.size(); i++) {
                if (indexName.equals(carList.get(i).getInitial())) {
                    manager.scrollToPositionWithOffset(i, 0);
                    return;
                }
            }
        });

    }

    @Override
    public void initListener() {
        selectTv.setOnClickListener(this);
        search_name.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(getString(R.string.personnel_list));
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
    public boolean isTitle() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.selectTv:
                //pop
                break;
            case R.id.search_name:
                SearchVipActivity.Start(this);
                break;
        }
    }
}
