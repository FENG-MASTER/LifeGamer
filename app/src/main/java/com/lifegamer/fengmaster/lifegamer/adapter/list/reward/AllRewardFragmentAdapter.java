package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/22.
 */

public class AllRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    @Override
    public String getName() {
        return "所有奖励";
    }


    @Override
    public void updateShowList() {
        showList= Game.getInstance().getRewardManager().getAllAvailableRewardItem();
    }
}
