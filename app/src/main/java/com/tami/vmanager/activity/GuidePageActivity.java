package com.tami.vmanager.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tami.vmanager.R;
import com.tami.vmanager.adapter.GuidePageFragmentPagerAdapter;
import com.tami.vmanager.fragment.GuidePageFragment;
import com.tami.vmanager.utils.Constants;
import com.tami.vmanager.view.ViewPagerIndicator;

/**
 * 引导页
 * Created by why on 2018/6/11.
 */

public class GuidePageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerIndicator viewPagerIndicator;
    private ViewPagerOnPageChangeListener viewPagerOnPageChangeListener;
    private Fragment[] arrayFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        hideBottomUIMenu();

        setContentView(R.layout.activity_guidepage);

        viewPager = findViewById(R.id.viewpage_id);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);

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
        viewPagerOnPageChangeListener = new ViewPagerOnPageChangeListener(viewPagerIndicator);
        viewPager.addOnPageChangeListener(viewPagerOnPageChangeListener);
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPager.removeOnPageChangeListener(viewPagerOnPageChangeListener);
        viewPager.removeAllViews();
        arrayFragment = null;
        viewPager = null;
    }

    /**
     * //     * ViewPager滚动监听
     * //
     */
    public class ViewPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private ViewPagerIndicator viewPagerIndicator;

        public ViewPagerOnPageChangeListener(ViewPagerIndicator viewPagerIndicator) {
            this.viewPagerIndicator = viewPagerIndicator;
        }

        @Override
        public void onPageSelected(int position) {
            viewPagerIndicator.setSelectPosition(position);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            viewPagerIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int position) {
            if (position == 0) {
                viewPagerIndicator.onPageScrollEnd();
            }
        }
    }
}
