package com.tami.vmanager.activity;

import android.view.View;
import android.widget.TextView;

import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.ListViewAdapter;
import com.tami.vmanager.adapter.ViewHolder;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.SendVerifyCodeRequest;
import com.tami.vmanager.entity.SendVerifyCodeResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.view.swipemenu.SwipeHorizontalMenuLayout;
import com.tami.vmanager.view.swipemenu.SwipeMenuLayout;
import com.tami.vmanager.view.swipemenu.SwipeMenuListView;
import com.tami.vmanager.view.swipemenu.listener.SwipeRightSwitchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的创建
 * Created by why on 2018/7/5.
 */
public class MyCreateActivity extends BaseActivity {

    private PullToRefreshLayout pullToRefreshLayout;
    private SwipeMenuListView swipeMenuListView;
    private ListViewAdapter<String> listViewAdapter;
    private List<String> list;
    private NetworkBroker networkBroker;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_create;
    }

    @Override
    public void initView() {
        pullToRefreshLayout = findViewById(R.id.amc_PullToRefreshLayout);
        swipeMenuListView = findViewById(R.id.listView);
    }

    @Override
    public void initListener() {
        pullToRefreshLayout.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {
            }

            @Override
            public void loadMore() {
            }
        });
    }

    @Override
    public void initData() {
        setTitleName(R.string.my_create);

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());

        list = new ArrayList<>();
        list.add("测试");
        list.add("测试");
        list.add("测试");

        listViewAdapter = new ListViewAdapter<String>(getApplication(), list, R.layout.item_my_create) {
            @Override
            protected void binView(ViewHolder viewHolder, String item, int position) {
                SwipeHorizontalMenuLayout shmlView = viewHolder.getItemView(R.id.imc_shml);
                shmlView.setSwipeEnable(true);
                //修改按钮
                TextView modifyView = viewHolder.getItemView(R.id.menu_modify);
                modifyView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    showToast("modifyView");
                });
                //取消按钮
                TextView cancelView = viewHolder.getItemView(R.id.menu_cancel);
                cancelView.setOnClickListener((View v) -> {
                    shmlView.smoothCloseEndMenu();
                    showToast("cancelView");
                });
                //编辑按钮
                TextView editView = viewHolder.getItemView(R.id.item_content_editor);
                editView.setOnClickListener((View v) -> {
                    shmlView.smoothOpenEndMenu();
                });

                shmlView.setSwipeListener(new SwipeRightSwitchListener() {
                    @Override
                    public void endMenuClosed(SwipeMenuLayout swipeMenuLayout) {
                        editView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void endMenuOpened(SwipeMenuLayout swipeMenuLayout) {
                        editView.setVisibility(View.GONE);
                    }
                });
            }
        };
        swipeMenuListView.setAdapter(listViewAdapter);
        pullToRefreshLayout.setCanRefresh(false);
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


    /**
     * 获取我的创建数据
     */
    private void getMyCreateList() {
        SendVerifyCodeRequest sendVerifyCodeRequest = new SendVerifyCodeRequest();
        networkBroker.ask(sendVerifyCodeRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                SendVerifyCodeResponse response = (SendVerifyCodeResponse) res;
                if (response.getCode() == 200) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }
}
