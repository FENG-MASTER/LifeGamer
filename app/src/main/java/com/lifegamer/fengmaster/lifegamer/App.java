package com.lifegamer.fengmaster.lifegamer;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.GameBaseInitFinish;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/6.
 */

public class App extends Application {
    private static Context mContext;
    private Game game;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        game=Game.getInstance();
        EventBus.getDefault().post(new GameBaseInitFinish());
    }


    public static Context getContext(){
        return mContext;
    }


}
