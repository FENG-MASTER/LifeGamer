package com.lifegamer.fengmaster.lifegamer.event.reward;

import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 新建奖励事件
 */

public class NewRewardEvent {

    private RewardItem rewardItem;

    public NewRewardEvent(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }
}
