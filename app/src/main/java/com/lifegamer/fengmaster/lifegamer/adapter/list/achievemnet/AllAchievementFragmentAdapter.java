package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/23.
 */

public class AllAchievementFragmentAdapter extends BaseAchievementFragmentAdapter {
    @Override
    public String getName() {
        return App.getContext().getString(R.string.all_achievements);
    }

    @Override
    public void updateShowList() {
        showList= Game.getInstance().getAchievementManager().getAllAchievement();
    }
}
