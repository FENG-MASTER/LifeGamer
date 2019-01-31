package com.lifegamer.fengmaster.lifegamer.adapter.list.log;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * 所有日志足迹列表
 * Created by FengMaster on 19/01/28.
 */
public class AllLogAdapter extends BaseLogAdapter{
    @Override
    public void updateShowList() {
        showList= Game.getInstance().getLogManager().getAllLog();
    }


    @Override
    public String getName() {
        return "足迹";
    }
}
