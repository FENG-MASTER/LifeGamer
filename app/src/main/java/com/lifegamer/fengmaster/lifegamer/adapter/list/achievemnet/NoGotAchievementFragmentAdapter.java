package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * 未获得成就fragment
 * Created by FengMaster on 18/07/11.
 */
public class NoGotAchievementFragmentAdapter extends BaseAchievementFragmentAdapter {
    @Override
    public void updateShowList() {
        showList= Game.getInstance().getAchievementManager().getAllNoGetAchievment();
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.got_achievements);
    }
}
