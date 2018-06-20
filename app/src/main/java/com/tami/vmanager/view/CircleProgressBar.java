package com.tami.vmanager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.tami.vmanager.R;

/**
 * Created by wangyuchao on 15/7/29.
 */
public class CircleProgressBar extends View {
    private final int PROGRESS_MIN = 0;
    private final int PROGRESS_MAX = 100;
    /**
     * annotation : style : true represent pie , false represent ring
     */
    private boolean isSolid = false;
    /**
     * annotation : when isSolid is false , ProgressCircleView is a ring , this represent ring width
     */
    private float ringWidth = 1;
    /**
     * annotation : progress background color
     */
    private int progressBackgroundColor = Color.LTGRAY;
    /**
     * annotation : progress foreground color
     */
    private int progressForegroundColor = Color.DKGRAY;
    /**
     * annotation : progress value {0 - 100}
     */
    private float progress = 0;
    /**
     * annotation : text size
     */
    private float textSize = 13;
    /**
     * annotation : text color
     */
    private int textColor = progressForegroundColor;

    private boolean isTextShown;

    private Paint paintBackground = new Paint();
    private Paint paintForeground = new Paint();
    private Paint paintText = new Paint();
    private RectF rectF;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.parseAttributeSet(context, attrs);
    }

    private void parseAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
        if (array.hasValue(R.styleable.CircleProgressBar_backgroundColor)) {
            setProgressBackgroundColor(array.getColor(R.styleable.CircleProgressBar_backgroundColor, progressBackgroundColor));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_foregroundColor)) {
            setProgressForegroundColor(array.getColor(R.styleable.CircleProgressBar_foregroundColor, progressForegroundColor));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_isSolid)) {
            setSolid(array.getBoolean(R.styleable.CircleProgressBar_isSolid, isSolid));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_progress)) {
            setProgress(array.getFloat(R.styleable.CircleProgressBar_progress, progress));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_ringWidth)) {
            setRingWidth(array.getDimension(R.styleable.CircleProgressBar_ringWidth, ringWidth));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_textSize)) {
            setTextSize(array.getDimension(R.styleable.CircleProgressBar_textSize, textSize));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_textColor)) {
            setTextColor(array.getColor(R.styleable.CircleProgressBar_textColor, textColor));
        }
        if (array.hasValue(R.styleable.CircleProgressBar_textShown)) {
            setTextShown(array.getBoolean(R.styleable.CircleProgressBar_textShown, isTextShown));
        }
        array.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.rectF = new RectF();
        this.rectF.set(0 + ringWidth, 0 + ringWidth, w - ringWidth, h - ringWidth);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintBackground.setAntiAlias(true);
        paintForeground.setAntiAlias(true);
        paintBackground.setColor(progressBackgroundColor);
        paintForeground.setColor(progressForegroundColor);
        if (isSolid) {
            paintBackground.setStrokeWidth(0);
            paintForeground.setStrokeWidth(0);
            paintBackground.setStyle(Paint.Style.FILL);
            paintForeground.setStyle(Paint.Style.FILL);
            canvas.drawArc(this.rectF, -90, 360, true, paintBackground);
            canvas.drawArc(this.rectF, -90, (int) (progress / 100 * 360), true, paintForeground);
        } else {
            paintBackground.setStrokeWidth(ringWidth);
            paintBackground.setStyle(Paint.Style.STROKE);
            paintForeground.setStrokeWidth(ringWidth);
            paintForeground.setStyle(Paint.Style.STROKE);
            canvas.drawArc(this.rectF, -90, 360, false, paintBackground);
            canvas.drawArc(this.rectF, -90, (int) (progress / 100 * 360), false, paintForeground);
        }

        if (isTextShown) {
            paintText.setAntiAlias(true);
            paintText.setStyle(Paint.Style.FILL);
            paintText.setColor(textColor);
            paintText.setTextSize(textSize);
            paintText.setTypeface(Typeface.DEFAULT_BOLD);

            String text = (int)progress + "%";
            float textWidth = paintText.measureText(text);
            int centre = getWidth() / 2;
            canvas.drawText(text, centre - textWidth / 2, centre + textSize / 2, paintText);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
        postInvalidate();
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.progressBackgroundColor = progressBackgroundColor;
        postInvalidate();
    }

    public void setProgressForegroundColor(int progressForegroundColor) {
        this.progressForegroundColor = progressForegroundColor;
        postInvalidate();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if (progress < PROGRESS_MIN) {
            this.progress = PROGRESS_MIN;
        } else if (progress > PROGRESS_MAX) {
            this.progress = PROGRESS_MAX;
        }
        postInvalidate();
    }

    public void setRingWidth(float ringWidth) {
        if (ringWidth >= 1) {
            this.ringWidth = ringWidth;
        }
        postInvalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        postInvalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        postInvalidate();
    }

    public void setTextShown(boolean isTextShown) {
        this.isTextShown = isTextShown;
        postInvalidate();
    }
}
