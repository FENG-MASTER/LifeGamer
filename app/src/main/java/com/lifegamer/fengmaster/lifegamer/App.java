package com.lifegamer.fengmaster.lifegamer;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.Snackbar;
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


}
