package com.tami.vmanager.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.entity.LoginResponse;
import com.tami.vmanager.fragment.HomeFragment;
import com.tami.vmanager.fragment.HomeFragmentListener;
import com.tami.vmanager.fragment.MeetingFragment;
import com.tami.vmanager.fragment.PersonalCenterFragment;
import com.tami.vmanager.manager.ActivityManager;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.CheckUpdate;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.SPUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 主页
 * Created by why on 2018/6/12.
 */

public class HomeActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    private TabLayout home_tab;
    private String[] title = new String[]{"首页", "会议", "我的"};
    private int[] tabIconUnSelected = new int[]{R.mipmap.tab_home_nor, R.mipmap.tab_meeting_nor, R.mipmap.tab_me_nor};
    private int[] tabIconSelected = new int[]{R.mipmap.tab_home_pre, R.mipmap.tab_meeting_pre, R.mipmap.tab_me_pre};

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String currentContentFragmentTag = null;
    private int selectId = 0;

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        JPushInterface.init(getApplicationContext());
        LoginResponse.Item Login_item = (LoginResponse.Item) SPUtils.get(Constants.FILE_KEY, Constants.SAVE_LOGIN_DATA);
        if (Login_item != null){
            GlobaVariable.getInstance().item = Login_item;
        }
        home_tab = findViewById(R.id.home_tab);

        CheckUpdate.getInstance(this,0);
    }

    @Override
    public void initListener() {
        home_tab.addOnTabSelectedListener(this);
    }

    @Override
    public void initData() {
        //设置TabLayout的模式
        home_tab.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        for (int i = 0; i < title.length; i++) {
            home_tab.getTabAt(i).setCustomView(getTabView(title[i], tabIconUnSelected[i]));
        }
        onTabSelected(home_tab.getTabAt(selectId));
    }

    // Tab自定义view
    public View getTabView(String title, int image_src) {
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_tab, null);
        TextView textView = v.findViewById(R.id.item_tab_txt);
        textView.setText(title);
        ImageView imageView = v.findViewById(R.id.item_tab_img);
        imageView.setImageResource(image_src);
        return v;
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {
        home_tab.removeOnTabSelectedListener(this);
    }

    @Override
    public void emptyObject() {
        if(homeFragmentListener!=null){
            homeFragmentListener = null;
        }
    }

    public void onSelected(int selectId) {
        Logger.d("选择Fragment页面下标selectId为:" + selectId);
        try {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = null;
            String tag = null;
            if (currentContentFragmentTag != null) {
                final Fragment currentFragment = fragmentManager.findFragmentByTag(currentContentFragmentTag);
                if (currentFragment != null) {
                    fragmentTransaction.hide(currentFragment);
                }
            }

            switch (selectId) {
                case 0:
                    tag = HomeFragment.class.getSimpleName();
                    final Fragment homeFragment = fragmentManager.findFragmentByTag(tag);
                    if (homeFragment != null) {
                        fragment = homeFragment;
                    } else {
                        fragment = new HomeFragment();
                    }
                    if(homeFragmentListener!=null){
                        homeFragmentListener.tabOnCLick();
                    }
                    break;
                case 1:
                    tag = MeetingFragment.class.getSimpleName();
                    final Fragment meetingFragment = fragmentManager.findFragmentByTag(tag);
                    if (meetingFragment != null) {
                        fragment = meetingFragment;
                    } else {
                        fragment = new MeetingFragment();
                    }
                    break;
                case 2:
                    tag = PersonalCenterFragment.class.getSimpleName();
                    final Fragment personalCenterFragment = fragmentManager.findFragmentByTag(tag);
                    if (personalCenterFragment != null) {
                        fragment = personalCenterFragment;
                    } else {
                        fragment = new PersonalCenterFragment();
                    }
                    break;
                default:
                    break;
            }
            if (fragment != null && fragment.isAdded()) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.add(R.id.content_layout, fragment, tag);
            }
//            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commitAllowingStateLoss();
            currentContentFragmentTag = tag;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub super存在是处理Fragment
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TextView textView = tab.getCustomView().findViewById(R.id.item_tab_txt);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tab_selected));
        ImageView imageView = tab.getCustomView().findViewById(R.id.item_tab_img);
        imageView.setImageResource(tabIconSelected[tab.getPosition()]);
        onSelected(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView textView = tab.getCustomView().findViewById(R.id.item_tab_txt);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.tab_unselected));
        ImageView imageView = tab.getCustomView().findViewById(R.id.item_tab_img);
        imageView.setImageResource(tabIconUnSelected[tab.getPosition()]);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getString(R.string.one_more_exit_program));
            exitTime = System.currentTimeMillis();
        } else {
            ActivityManager.getInstance().finishAllActivity();
            System.exit(0);
        }
    }

    private HomeFragmentListener homeFragmentListener;

    public void setHomeFragmentListener(HomeFragmentListener hfl){
        this.homeFragmentListener= hfl;
    }

    public void removeHomeFragmentListener(){
        this.homeFragmentListener = null;
    }
}
