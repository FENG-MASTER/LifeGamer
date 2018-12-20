package com.lifegamer.fengmaster.lifegamer.util;

import android.content.Context;

import com.lifegamer.fengmaster.lifegamer.App;

/**
 * Created by qianzise on 2017/10/4.
 */

public class DensityUtil {

    /**
     * 根据手机分辨率从DP转成PX
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    public static int getPhoneWidthPx(){
        return App.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getPhoneHeightPx(){
        return App.getContext().getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 获取屏幕百分比高
     * @param d
     * @return
     */
    public static int getPhoneHeightPx(double d){
        return (int)Math.floor(getPhoneHeightPx()*d);
    }

    /**
     * 获取屏幕百分比宽
     * @param d
     * @return
     */
    public static int getPhoneWidthPx(double d){
        return (int)Math.floor(getPhoneWidthPx()*d);

    }


}
