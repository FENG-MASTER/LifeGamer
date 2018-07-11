package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/23.
 */

public class AllAchievementFragmentAdapter extends BaseAchievementFragmentAdapter {
    @Override
    public String getName() {
        return "所有";
    }

    @Override
    public void updateShowList() {
        showList= Game.getInstance().getAchievementManager().getAllAchievement();
    }
}
