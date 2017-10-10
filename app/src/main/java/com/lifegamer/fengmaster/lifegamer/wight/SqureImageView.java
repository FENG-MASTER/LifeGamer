package com.lifegamer.fengmaster.lifegamer.wight;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by qianzise on 2017/10/4.
 */

public class SqureImageView extends AppCompatImageView {
    public SqureImageView(Context context) {
        super(context);
    }

    public SqureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        if (width>height){
            width=height;
        }else {
            height=width;
        }

        int i = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int j = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        setMeasuredDimension(i, j);


    }
}
