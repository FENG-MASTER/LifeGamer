package com.lifegamer.fengmaster.lifegamer.event.reward;

import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

/**
 * 商店奖励被删除 事件
 */
public class DeleteRewardEvent {
    private RewardItem rewardItem;

    public DeleteRewardEvent(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }
}
