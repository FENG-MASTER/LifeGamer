package com.lifegamer.fengmaster.lifegamer.util;

import android.util.Log;

/**
 * 日志工具类
 * Created by FengMaster on 19/01/04.
 */
public class LogUtil {

    private static final boolean debug=true;

    private static final String TAG="lifeGamer";

    public static void e(String msg){
        Log.e(TAG,msg);
    }

    public static void i(String msg){
        if (!debug){
            return;
        }
        Log.i(TAG,msg);
    }




}
