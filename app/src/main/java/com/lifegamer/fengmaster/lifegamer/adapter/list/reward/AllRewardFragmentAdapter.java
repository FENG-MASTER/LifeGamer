package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/22.
 */

public class AllRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    @Override
    public String getName() {
        return "全部";
    }

    @Override
    public void updateRewardItemList() {
        rewardItemList= Game.getInstance().getRewardManager().getAllAvailableRewardItem();
    }
}
