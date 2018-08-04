package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tami.vmanager.R;
import com.tami.vmanager.adapter.SectionDecoration;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.GetUserDepartmentRequest;
import com.tami.vmanager.entity.GetUserDepartmentResponse;
import com.tami.vmanager.entity.GetUserInDepartmentResponse;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.entity.SetMeetingItemsUserJson;
import com.tami.vmanager.entity.SetMeetingItemsUserRequest;
import com.tami.vmanager.entity.SetMeetingItemsUserResponse;
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
 * 添加负责人
 * Created by why on 2018/7/5.
 */
public class AddPersonChargeActivty extends BaseActivity {

    private TextView people;//已选择人数
    private Button confirm;//确定
    private RecyclerView recyclerView;//列表
    private CommonAdapter<GetUserDepartmentResponse.Array.Item.User> commonAdapter;
    private List<GetUserDepartmentResponse.Array.Item.User> contentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();
    private NetworkBroker networkBroker;
    //统计
    private int count;
    private int meetingItemSetId;
    private int meetingId;

    @Override
    public boolean isTitle() {
        return true;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_person_charge;
    }

    @Override
    public void initView() {
        people = findViewById(R.id.aapc_people);
        confirm = findViewById(R.id.aapc_confirm);
        recyclerView = findViewById(R.id.aapc_recyclerview);
    }

    @Override
    public void initListener() {
        confirm.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            meetingId = intent.getIntExtra(Constants.KEY_MEETING_ID, 0);
            meetingItemSetId = intent.getIntExtra(Constants.KEY_MEETING_ITEM_SETID, -1);
        }
        setTitleName(getString(R.string.add_person_charge));

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
        if (contentList != null) {
            contentList.clear();
            contentList = null;
        }
        if (titleList != null) {
            titleList.clear();
            titleList = null;
        }
        if (networkBroker != null) {
            networkBroker.cancelAllRequests();
            networkBroker = null;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.aapc_confirm:
                confirm.setEnabled(false);
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
        commonAdapter = new CommonAdapter<GetUserDepartmentResponse.Array.Item.User>(getApplicationContext(), R.layout.item_add_person_charge, contentList) {
            @Override
            protected void convert(ViewHolder holder, GetUserDepartmentResponse.Array.Item.User user, int position) {
                holder.setText(R.id.iapc_position, user.depName);
                holder.setText(R.id.iapc_name, user.realName);
                AppCompatImageView selectImage = holder.getView(R.id.iapc_select_image);
                selectImage.setImageResource(user.status == 1 ? R.mipmap.people_checkbox_selected : R.mipmap.people_checkbox_unselected);
                holder.getView(R.id.iapc_layout).setOnClickListener((View v) -> {
                    user.status = user.status == 1 ? 0 : 1;
                    selectImage.setImageResource(user.status == 1 ? R.mipmap.people_checkbox_selected : R.mipmap.people_checkbox_unselected);
                    setPeople(count = user.status == 1 ? ++count : --count);
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
        GetUserDepartmentRequest guidr = new GetUserDepartmentRequest();
        LoginResponse.Item item = GlobaVariable.getInstance().item;
        if (item != null) {
            guidr.setUserId(item.getId());
        }
        guidr.setMeetingItemSetId(meetingItemSetId);
        networkBroker.ask(guidr, (ex1, res) -> {
            if (null != ex1) {
                Logger.d(ex1.getMessage() + "-" + ex1);
                return;
            }
            try {
                GetUserDepartmentResponse response = (GetUserDepartmentResponse) res;
                if (response.getCode() == 200) {
                    if (response.data != null) {
                        GetUserDepartmentResponse.Array guidrItem = response.data;
                        if (guidrItem.dataList != null) {
                            List<GetUserDepartmentResponse.Array.Item> guidrList = guidrItem.dataList;
                            if (guidrList != null) {
                                for (GetUserDepartmentResponse.Array.Item tieleItem : guidrList) {
                                    if (tieleItem.userList != null) {
                                        List<GetUserDepartmentResponse.Array.Item.User> userList = tieleItem.userList;
                                        if (userList != null) {
                                            for (GetUserDepartmentResponse.Array.Item.User userData : userList) {
                                                contentList.add(userData);
                                                if (userData.status == 1) {
                                                    count++;
                                                }
                                                if (!TextUtils.isEmpty(tieleItem.depName)) {
                                                    titleList.add(tieleItem.depName);
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
        ArrayList<SetMeetingItemsUserJson> data = new ArrayList<>();
        for (GetUserDepartmentResponse.Array.Item.User item : contentList) {
            if (item.status == 1) {
                SetMeetingItemsUserJson smiuj = new SetMeetingItemsUserJson();
                smiuj.setId(item.id);
                smiuj.setRealName(item.realName);
                data.add(smiuj);
            }
        }
        setMeetingItemsUser(data);
    }

    /**
     * 添加负责人
     */
    public void setMeetingItemsUser(ArrayList<SetMeetingItemsUserJson> data) {
        if (data != null && data.size() > 0) {
            SetMeetingItemsUserRequest siur = new SetMeetingItemsUserRequest();
            siur.setMeetingId(meetingId);
            siur.setMeetingItemSetId(meetingItemSetId);
            siur.setUserJsonStr(new Gson().toJson(data));
            networkBroker.ask(siur, (ex1, res) -> {
                if (null != ex1) {
                    Logger.d(ex1.getMessage() + "-" + ex1);
                    confirm.setEnabled(true);
                    return;
                }
                try {
                    SetMeetingItemsUserResponse response = (SetMeetingItemsUserResponse) res;
                    if (response.getCode() == 200 && response.data) {
                        Intent intent = new Intent();
                        intent.putExtra(Constants.RESULT_JIEDAIREN, true);
                        setResult(Constants.ADD_PERSON_CHARGE, intent);
                        finish();
                    } else {
                        showToast(response.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                confirm.setEnabled(true);
            });
        } else {
            confirm.setEnabled(true);
            Intent intent = new Intent();
            intent.putExtra(Constants.RESULT_JIEDAIREN, false);
            setResult(Constants.ADD_PERSON_CHARGE, intent);
            finish();
        }
    }

}
