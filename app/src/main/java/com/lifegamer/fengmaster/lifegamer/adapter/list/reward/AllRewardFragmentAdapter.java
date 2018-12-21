package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/22.
 */

public class AllRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    @Override
    public String getName() {
        return App.getContext().getString(R.string.reward_all);
    }


    @Override
    public void updateShowList() {
        showList= Game.getInstance().getRewardManager().getAllRewardItem();
    }
}
