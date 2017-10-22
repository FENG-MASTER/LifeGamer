package com.lifegamer.fengmaster.lifegamer.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.lifegamer.fengmaster.lifegamer.App;

import java.lang.ref.SoftReference;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by qianzise on 2017/10/8.
 *
 * 一些常用view相关的工具类
 */

public class ViewUtil {
    /**
     * 因为需要在多个CoordinatorLayout中实现Snack的显示,所以需要用个栈 原因自己想
     */
    private static Stack<SoftReference<View>> coopViews=new Stack<>();

    public static void addCoopView(View coopView) {
        coopViews.add(new SoftReference<View>(coopView));
    }

    public static void removeCoopView(View coopView){
        SoftReference<View> reference=null;
        for (SoftReference<View> r : coopViews) {
            if (r.get()==coopView){
                reference=r;
            }
        }
        coopViews.remove(reference);

    }

    /**
     * 显示snack说明
     * @param message 消息
     */
    public static void showSnack(String message){
        Snackbar.make(coopViews.peek().get(),message,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 显示snack说明
     * @param message 消息
     * @param time 显示时间
     */
    public static void showSnack(String message, int time){
        Snackbar.make(coopViews.peek().get(),message,time).show();
    }

    /**
     * 显示snack说明
     * @param message 消息
     * @param time 显示时间
     * @param actionName 动作文本
     * @param listener 动作回调
     */
    public static void showSnack(String message, int time, String actionName, View.OnClickListener listener){
        Snackbar.make(coopViews.peek().get(),message,time).setAction(actionName,listener).show();
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
