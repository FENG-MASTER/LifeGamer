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
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
//        if (width>height){
//            width=height;
//        }else {
//            height=width;
//        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width,widthMode), MeasureSpec.makeMeasureSpec(height,heightMode));
    }
}
