package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * 已获得成就fragment
 * Created by FengMaster on 18/07/11.
 */
public class GotAchievementFragmentAdapter extends BaseAchievementFragmentAdapter {
    @Override
    public void updateAchievements() {
        achievements= Game.getInstance().getAchievementManager().getAllGotAchievement();
    }

    @Override
    public String getName() {
        return "已获得成就";
    }
}
