package com.tami.vmanager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.base.BaseActivity;
import com.tami.vmanager.fragment.GuidePageFragment;
import com.tami.vmanager.manager.ActivityManager;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.utils.SharePreferenceTools;
import com.tami.vmanager.view.ViewPagerIndicator;

/**
 * 引导页
 * Created by why on 2018/6/11.
 */
public class GuidePageActivity extends BaseActivity {

    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPagerOnPageChangeListener viewPagerOnPageChangeListener;
    private Fragment[] arrayFragment;

    @Override
    public int getStartBarColor() {
        return android.R.color.transparent;
    }

    @Override
    public int getNavigationBarColor() {
        return android.R.color.transparent;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_guidepage;
    }

    @Override
    public void initView() {
        viewPager = findViewById(R.id.viewpage_id);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
    }

    @Override
    public void initListener() {
        viewPagerOnPageChangeListener = new ViewPagerOnPageChangeListener(viewPagerIndicator);
        viewPager.addOnPageChangeListener(viewPagerOnPageChangeListener);
    }

    @Override
    public void initData() {
        arrayFragment = new Fragment[3];

        GuidePageFragment aFragment = new GuidePageFragment();
        Bundle aBundle = new Bundle();
        aBundle.putString(Constants.IMAGE_KEY, Constants.IMAGE_A);
        aBundle.putBoolean(Constants.SKIP_BTN, true);
        aBundle.putBoolean(Constants.EXPERIENCE, false);
        aFragment.setArguments(aBundle);
        arrayFragment[0] = aFragment;

        GuidePageFragment bFragment = new GuidePageFragment();
        Bundle bBundle = new Bundle();
        bBundle.putString(Constants.IMAGE_KEY, Constants.IMAGE_B);
        bBundle.putBoolean(Constants.SKIP_BTN, true);
        bBundle.putBoolean(Constants.EXPERIENCE, false);
        bFragment.setArguments(bBundle);
        arrayFragment[1] = bFragment;

        GuidePageFragment cFragment = new GuidePageFragment();
        Bundle cBundle = new Bundle();
        cBundle.putString(Constants.IMAGE_KEY, Constants.IMAGE_C);
        cBundle.putBoolean(Constants.SKIP_BTN, false);
        cBundle.putBoolean(Constants.EXPERIENCE, true);
        cFragment.setArguments(cBundle);
        arrayFragment[2] = cFragment;

        GuidePageFragmentPagerAdapter guidePageFragmentPagerAdapter = new GuidePageFragmentPagerAdapter(getSupportFragmentManager(), arrayFragment);
        viewPager.setAdapter(guidePageFragmentPagerAdapter);

        handler.sendEmptyMessage(1);
//        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void requestNetwork() {

    }

    @Override
    public void removeListener() {
        viewPager.removeOnPageChangeListener(viewPagerOnPageChangeListener);
    }

    @Override
    public void emptyObject() {
        ActivityManager.getInstance().removeActivity(this);
        for (Fragment fragment : arrayFragment) {
            fragment = null;
        }
        arrayFragment = null;
        viewPagerOnPageChangeListener.removeViewPagerIndicator();
        viewPagerOnPageChangeListener = null;
        viewPagerIndicator = null;
        viewPager = null;
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                SharePreferenceTools sharePreferenceTools = new SharePreferenceTools(GuidePageActivity.this);
                boolean firstLanding = sharePreferenceTools.getBoolean(Constants.FIRST_LANDING);
                if (!firstLanding) {
                    sharePreferenceTools.putBoolean(Constants.FIRST_LANDING, true);
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }
        }
    };

    /**
     * //     * ViewPager滚动监听
     * //
     */
    public static class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private ViewPagerIndicator viewPagerIndicator;

        public ViewPagerOnPageChangeListener(ViewPagerIndicator viewPagerIndicator) {
            this.viewPagerIndicator = viewPagerIndicator;
        }

        public void removeViewPagerIndicator() {
            this.viewPagerIndicator = null;
        }

        @Override
        public void onPageSelected(int position) {
            if (viewPagerIndicator != null) {
                viewPagerIndicator.setSelectPosition(position);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (viewPagerIndicator != null) {
                viewPagerIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int position) {
            if (position == 0 && viewPagerIndicator != null) {
                viewPagerIndicator.onPageScrollEnd();
            }
        }
    }
}
