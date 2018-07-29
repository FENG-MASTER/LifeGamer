package com.lifegamer.fengmaster.lifegamer.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 带有两个文本显示的进度条
 */

public class ProgressBar extends RelativeLayout {
    private Paint paint;

    private int finishColor = Color.BLACK;
    private int unFinshColor = Color.WHITE;

    private float textSize;
    private int leftTextColor;
    private int rightTextColor;

    private String leftText;
    private String rightText;
    private String centerText;

    private int progress = 0;
    private int max = 100;

    @BindView(R.id.tv_progress_left)
    TextView left;
    @BindView(R.id.tv_progress_right)
    TextView right;
    @BindView(R.id.tv_progress_center)
    TextView center;


    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar, defStyleAttr, 0);

        progress = typedArray.getInt(R.styleable.ProgressBar_progress, 0);
        max = typedArray.getInt(R.styleable.ProgressBar_max, 100);
        finishColor = typedArray.getColor(R.styleable.ProgressBar_finishColor, Color.BLACK);
        unFinshColor = typedArray.getColor(R.styleable.ProgressBar_unFinishColor, Color.WHITE);
        textSize = typedArray.getDimension(R.styleable.ProgressBar_textSize, 15f);
        leftTextColor = typedArray.getColor(R.styleable.ProgressBar_leftTextColor, Color.BLACK);
        rightTextColor = typedArray.getColor(R.styleable.ProgressBar_rightTextColor, Color.BLACK);
        leftText=typedArray.getString(R.styleable.ProgressBar_leftText);
        rightText=typedArray.getString(R.styleable.ProgressBar_rightText);
        centerText=typedArray.getString(R.styleable.ProgressBar_centerText);
        typedArray.recycle();
        typedArray = null;

        init();
    }


    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.wight_progressbar, this);
        ButterKnife.bind(this, view);

        left.setTextColor(leftTextColor);
        right.setTextColor(rightTextColor);
        left.setTextSize(textSize);
        right.setTextSize(textSize);
        setCenterText(centerText);

        paint = new Paint();
        paint.setStrokeWidth(getHeight());
        paint.setColor(finishColor);
        paint.setStyle(Paint.Style.FILL);


        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(unFinshColor);
        canvas.drawRect(0, 0, getWidth() * (1.0f * progress / max), getHeight(), paint);
        super.dispatchDraw(canvas);
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }


    public void setLeftText(String text) {
        leftText=text;
        left.setText(leftText);
    }

    public void setRightText(String text) {
        rightText=text;
        right.setText(rightText);
    }

    public void setCenterText(String text){
        centerText=text;
        center.setText(centerText);
    }
}
