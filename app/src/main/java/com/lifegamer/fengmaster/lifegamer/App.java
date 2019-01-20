package com.lifegamer.fengmaster.lifegamer;

import android.app.Application;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.GameBaseInitFinish;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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
        game.initTaskManager();
        EventBus.getDefault().post(new GameBaseInitFinish());
        UMConfigure.init(getApplicationContext(),"58d6268e07fe65548a0011a6", "test",UMConfigure.DEVICE_TYPE_PHONE, null);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }


    public static Context getContext(){
        return mContext;
    }


}
