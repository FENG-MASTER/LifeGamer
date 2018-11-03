package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/22.
 */

public class AllAvailableRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    @Override
    public String getName() {
        return "可获得奖励";
    }


    @Override
    public void updateShowList() {
        showList= Game.getInstance().getRewardManager().getAllAvailableRewardItem();
    }
}
