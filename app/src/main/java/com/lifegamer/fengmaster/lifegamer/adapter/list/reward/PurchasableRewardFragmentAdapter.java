package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;


/**
 * 可购买的 物品
 */
public class PurchasableRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    @Override
    public String getName() {
        return "可购买的";
    }


    @Override
    public void updateShowList() {
        showList= Stream.of(Game.getInstance().getRewardManager().getAllRewardItem()).filter(value -> value.isPurchasable()).toList();
    }
}
