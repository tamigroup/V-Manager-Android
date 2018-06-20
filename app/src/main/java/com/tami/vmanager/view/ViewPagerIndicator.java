package com.tami.vmanager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.tami.vmanager.R;
import com.tami.vmanager.utils.Logger;
import com.tami.vmanager.utils.ScreenUtil;

public class ViewPagerIndicator extends View {
    // 画前景点的画笔
    private Paint dianPaint;
    // 画背景点的画笔
    private Paint bgPaint;
    // 点的数量
    private int dianCount;
    // 点的大小(直径)
    private int dianSize;
    // 前景点的颜色
    private int dianColor;
    // 背景点的颜色
    private int dianBgColor;
    // 两点之间的间隔大小
    private int margin = 8;
    // 当前选中的位置
    private int selectPosition = 0;
    // 对调选中位置改变
    private OnSelectChangeListener mListener;
    // ---------分割线，下面是滚动需要的变量----------
    // 可以滚动
    private final int STATE_READY = 100;
    // 正在滚动
    private final int STATE_SCROLLING = 101;
    // 当前滚动状态
    private int scrollState = STATE_READY;
    // 保存滚动时，前景点左边距离的计算结果
    private float scrollDianCX;

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        dianCount = array.getInteger(R.styleable.Indicator_indicator_count, 1);
        // 红色
        dianColor = array.getColor(R.styleable.Indicator_indicator_color, context.getResources().getColor(R.color.indicator_selected_color));
        // 白色
        dianBgColor = array.getColor(R.styleable.Indicator_indicator_bg_color, context.getResources().getColor(R.color.indicator_unselected_color));
        margin = array.getInteger(R.styleable.Indicator_indicator_margin, 20);
        margin = ScreenUtil.dip2px(context, 30f);
        dianSize = array.getInteger(R.styleable.Indicator_indicator_size, 20);
        dianSize = ScreenUtil.dip2px(context, 15f);
        array.recycle();
        init();
    }

    public void setDianCount(int dianCount) {
        Logger.d("设置滚动圆点dianCount:" + dianCount);
        this.dianCount = dianCount;
    }

    public void setDianSize(int dianSize) {
        this.dianSize = dianSize;
    }

    public void setDianColor(int dianColor) {
        this.dianColor = dianColor;
        dianPaint.setColor(dianColor);
    }

    public void setDianBgColor(int dianBgColor) {
        this.dianBgColor = dianBgColor;
        bgPaint.setColor(dianBgColor);
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    private void init() {
        // 初始化两支画笔
        dianPaint = new Paint();
        dianPaint.setAntiAlias(true);
        dianPaint.setColor(dianColor);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(dianBgColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量自身大小
        // 宽=点的宽度(直径)*点的个数+点之间的距离*（点的个数-1）
        int width = dianSize * dianCount + margin * (dianCount - 1);
        // 高=点的高度(直径)
        int height = dianSize;
        int wMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int hMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        // 设置计算结果
        setMeasuredDimension(wMeasureSpec, hMeasureSpec);
    }

    @Override
    public void draw(Canvas canvas) {
        // 画背景点
        for (int i = 0; i < dianCount; i++) {
            drawBgDian(canvas, i);
        }
        // 如果不是在滚动状态，画选中位置的前景点
        if (selectPosition > -1 && scrollState != STATE_SCROLLING) {
            drawDian(canvas);
        }
        // 在滚动状态，画滚动点
        if (scrollState == STATE_SCROLLING) {
            drawScrollDian(canvas);
        }
    }

    // 画没在滚动时被选中的点
    private void drawDian(Canvas canvas) {
        canvas.drawCircle((dianSize + margin) * selectPosition + dianSize / 2, dianSize / 2, dianSize / 2, dianPaint);
    }

    private void drawBgDian(Canvas canvas, int i) {
        canvas.drawCircle((dianSize + margin) * i + dianSize / 2, dianSize / 2, dianSize / 2, bgPaint);
    }

    private void drawScrollDian(Canvas canvas) {
        canvas.drawCircle(scrollDianCX, dianSize / 2, dianSize / 2, dianPaint);
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        // 外部可以设置监听选中位置的改变，一般用不着，直接监听ViewPager的就行了
        if (mListener != null)
            mListener.onSelectChange(selectPosition);
        // 如果当前正在滚动，就没必要请求重绘了，等到滚动结束后会去请求重绘的
        if (scrollState == STATE_SCROLLING)
            return;
        invalidate();
    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (arg1 > 0 && arg1 < 1) {
            scrollState = STATE_SCROLLING;
            // 滚动时计算前景点距离左边的距离
            scrollDianCX = (dianSize + margin) * (float) (arg0 + arg1) + dianSize / 2;
            invalidate();
        }
    }

    public void onPageScrollEnd() {
        scrollState = STATE_READY;
        invalidate();
    }

    public void setSelectChangeListener(OnSelectChangeListener l) {
        mListener = l;
    }

    interface OnSelectChangeListener {
        void onSelectChange(int newPos);
    }

}
