package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.SectionDecoration;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.UserListOfPositionRequest;
import com.tami.vmanager.entity.UserListOfPositionResponse;
import com.tami.vmanager.http.NetworkBroker;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加接待人
 * <p>
 * Created by why on 2018/6/27.
 */
public class AddReceptionistActivity extends BaseActivity {

    private TextView people;//已选择人数
    private Button confirm;//确定
    private RecyclerView recyclerView;//列表
    private CommonAdapter<UserListOfPositionResponse.Item.TitleItem.ContentList> commonAdapter;
    private List<UserListOfPositionResponse.Item.TitleItem.ContentList> contentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private NetworkBroker networkBroker;
    //统计
    private int count;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_receptionist;
    }

    @Override
    public void initView() {
        people = findViewById(R.id.aar_people);
        confirm = findViewById(R.id.aar_confirm);
        recyclerView = findViewById(R.id.aar_recyclerview);
    }

    @Override
    public void initListener() {
        confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {
        setTitleName(R.string.add_receptionist);
        //初始化选择人数
        setPeople(count);
        initRecyclerView();

        networkBroker = new NetworkBroker(this);
        networkBroker.setCancelTag(getTAG());
    }

    @Override
    public void requestNetwork() {
        getListData();
    }

    @Override
    public void removeListener() {

    }

    @Override
    public void emptyObject() {
        networkBroker.cancelAllRequests();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.aar_confirm:
                confirm();
                break;
        }
    }

    /**
     * 初始化选择人数
     *
     * @param num
     */
    private void setPeople(int num) {
        String content = getString(R.string.selected_people, num);
        int end = 4 + String.valueOf(num).length();
        people.setText(Utils.getSplicingColor(getApplicationContext(), content, 4, end, R.color.color_FF5657));
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commonAdapter = new CommonAdapter<UserListOfPositionResponse.Item.TitleItem.ContentList>(getApplicationContext(), R.layout.item_add_receptionist, contentList) {
            @Override
            protected void convert(ViewHolder holder, UserListOfPositionResponse.Item.TitleItem.ContentList contentList, int position) {
                holder.setText(R.id.iar_name, contentList.realName);
                AppCompatImageView selectImage = holder.getView(R.id.iar_select_image);
                selectImage.setImageResource(contentList.isSelected ? R.mipmap.people_checkbox_selected : R.mipmap.people_checkbox_unselected);
                holder.getView(R.id.rar_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contentList.isSelected = !contentList.isSelected;
                        selectImage.setImageResource(contentList.isSelected ? R.mipmap.people_checkbox_selected : R.mipmap.people_checkbox_unselected);
                        setPeople(count = contentList.isSelected ? ++count : --count);
                    }
                });
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SectionDecoration(contentList, this, new SectionDecoration.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (titleList.get(position) != null) {
                    return titleList.get(position);
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (titleList.get(position) != null) {
                    return titleList.get(position);
                }
                return "";
            }
        }));
        recyclerView.setAdapter(commonAdapter);
    }

    /**
     * 获取数据
     */
    private void getListData() {
        UserListOfPositionRequest userListOfPositionRequest = new UserListOfPositionRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            userListOfPositionRequest.setSystemId(item.getSystemId());
        }
        networkBroker.ask(userListOfPositionRequest, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                UserListOfPositionResponse response = (UserListOfPositionResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        UserListOfPositionResponse.Item ulprItem = response.data;
                        if (ulprItem.dataList != null) {
                            List<UserListOfPositionResponse.Item.TitleItem> dataList = ulprItem.dataList;
                            if (dataList != null) {
                                for (UserListOfPositionResponse.Item.TitleItem tieleItem : dataList) {
                                    if (tieleItem.userList != null) {
                                        List<UserListOfPositionResponse.Item.TitleItem.ContentList> userList = tieleItem.userList;
                                        if (userList != null) {
                                            for (UserListOfPositionResponse.Item.TitleItem.ContentList contentdata : userList) {
                                                contentList.add(contentdata);
                                                if (contentdata.isSelected) {
                                                    count++;
                                                }
                                                if (!TextUtils.isEmpty(tieleItem.departmentName)) {
                                                    titleList.add(tieleItem.departmentName);
                                                } else {
                                                    titleList.add("分组名称为空");
                                                }
                                            }
                                        }
                                    }
                                }
                                setPeople(count);
                                commonAdapter.notifyDataSetChanged();
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
     * 提交
     */
    private void confirm() {
        ArrayList<UserListOfPositionResponse.Item.TitleItem.ContentList> data = new ArrayList<>();
        for (UserListOfPositionResponse.Item.TitleItem.ContentList item : contentList) {
            if (item.isSelected) {
                data.add(item);
            }
        }
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.RESULT_JIEDAIREN, data);
        setResult(Constants.CREATE_MEETING_JIEDAIREN, intent);
        finish();
    }
}
