package com.lifegamer.fengmaster.lifegamer;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;

/**
 * Created by qianzise on 2017/10/6.
 */

public class App extends Application {
    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    public static void showToast(String message,int time){
        Toast.makeText(mContext,message,time).show();
    }

    public static void showToast(String message){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
    }
}
