package com.lifegamer.fengmaster.lifegamer.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.lifegamer.fengmaster.lifegamer.App;

import java.lang.ref.SoftReference;

/**
 * Created by qianzise on 2017/10/8.
 *
 * 一些常用view相关的工具类
 */

public class ViewUtil {

    private static SoftReference<View> coopView;

    public static void setCoopView(View coopView) {
        ViewUtil.coopView = new SoftReference<View>(coopView);
    }

    /**
     * 显示snack说明
     * @param message 消息
     */
    public static void showSnack(String message){
        Snackbar.make(coopView.get(),message,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示snack说明
     * @param message 消息
     * @param time 显示时间
     */
    public static void showSnack(String message, int time){
        Snackbar.make(coopView.get(),message,time).show();
    }

    /**
     * 显示snack说明
     * @param message 消息
     * @param time 显示时间
     * @param actionName 动作文本
     * @param listener 动作回调
     */
    public static void showSnack(String message, int time, String actionName, View.OnClickListener listener){
        Snackbar.make(coopView.get(),message,time).setAction(actionName,listener).show();
    }

    /**
     * 显示toast
     * @param message 消息
     * @param time 显示时间
     */
    public static void showToast(String message,int time){
        Toast.makeText(App.getContext(),message,time).show();
    }

    /**
     * 显示toast
     * @param message 消息
     */
    public static void showToast(String message){
        Toast.makeText(App.getContext(),message,Toast.LENGTH_SHORT).show();
    }
}
