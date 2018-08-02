package com.tami.vmanager.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import com.tami.vmanager.R;
import com.tami.vmanager.entity.GetUserDepartmentResponse;
import com.tami.vmanager.entity.GetUserInDepartmentResponse;
import com.tami.vmanager.entity.UserListOfPositionResponse;
import com.tami.vmanager.utils.ScreenUtil;
import java.util.List;

public class SectionDecoration extends RecyclerView.ItemDecoration {

    private List<GetUserDepartmentResponse.Array.Item.User> dataList;
    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private int txtLeft;
    private Paint.FontMetrics fontMetrics;


    public SectionDecoration(List<GetUserDepartmentResponse.Array.Item.User> dataList, Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.dataList = dataList;
        this.callback = decorationCallback;
        //设置悬浮栏的画笔---paint
        paint = new Paint();
        paint.setColor(res.getColor(R.color.default_bg));

        //设置悬浮栏中文本的画笔
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(ScreenUtil.dip2px(context, 16));
        textPaint.setColor(ContextCompat.getColor(context, R.color.color_303030));
        textPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = textPaint.getFontMetrics();

        //决定悬浮栏的高度等
        topGap = res.getDimensionPixelSize(R.dimen.dp_50);
        //决定文本的显示位置等
        txtLeft = res.getDimensionPixelSize(R.dimen.dp_10);
    }

    //图1：代表了getItemOffsets(),可以实现类似padding的效果
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);

        String groupId = callback.getGroupId(pos);
        if (groupId.equals("-1")) return;
        //只有是同一组的第一个才显示悬浮栏
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap;
            if (dataList.get(pos).realName == "") {
                outRect.top = 0;
            }
        } else {
            outRect.top = 0;
        }
    }

    //图2：代表了onDraw(),可以实现类似绘制背景的效果，内容在上面
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            String groupId = callback.getGroupId(position);
            if (groupId.equals("-1")) return;
            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (textLine == "") {
                float top = view.getTop();
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);
                return;
            } else {
                if (position == 0 || isFirstInGroup(position)) {
                    float top = view.getTop() - topGap;
                    float bottom = view.getTop();
                    //绘制悬浮栏
                    c.drawRect(left, top, right, bottom, paint);
                    Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
                    int baseline = (int) top - topGap + (topGap + fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.top;
                    //绘制文本
                    c.drawText(textLine, txtLeft, baseline, textPaint);
                }
            }
        }
    }

    //图3：代表了onDrawOver()，可以绘制在内容的上面，覆盖内容
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        String preGroupId = "";
        String groupId = "-1";
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId.equals("-1") || groupId.equals(preGroupId)) continue;

            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

            int viewBottom = view.getBottom();
            float textY = Math.max(topGap, view.getTop());
            //下一个和当前不一样移动当前
            if (position + 1 < itemCount) {
                String nextGroupId = callback.getGroupId(position + 1);
                //组内最后一个view进入了header
                if (nextGroupId != groupId && viewBottom < textY) {
                    textY = viewBottom;
                }
            }
            //textY - topGap决定了悬浮栏绘制的高度和位置
            c.drawRect(left, textY - topGap, right, textY, paint);

            Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
            int baseline = (int) textY - topGap + (topGap + fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.top;
            c.drawText(textLine, txtLeft, baseline, textPaint);
        }
    }


    /**
     * 判断是不是组中的第一个位置
     *
     * @param pos
     * @return
     */
    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            // 因为是根据 字符串内容的相同与否 来判断是不是同意组的，所以此处的标记id 要是String类型
            // 如果你只是做联系人列表，悬浮框里显示的只是一个字母，则标记id直接用 int 类型就行了
            String prevGroupId = callback.getGroupId(pos - 1);
            String groupId = callback.getGroupId(pos);
            //判断前一个字符串 与 当前字符串 是否相同
            if (prevGroupId.equals(groupId)) {
                return false;
            } else {
                return true;
            }
        }
    }

    //定义一个借口方便外界的调用
    public interface DecorationCallback {
        String getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}