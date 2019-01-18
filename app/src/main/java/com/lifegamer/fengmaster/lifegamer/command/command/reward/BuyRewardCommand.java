package com.lifegamer.fengmaster.lifegamer.command.command.reward;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

/**
 * 购买奖励物品命令
 * Created by FengMaster on 19/01/19.
 */
public class BuyRewardCommand extends AbsNoCancelableCommand {
    private RewardItem rewardItem;

    public BuyRewardCommand(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getRewardManager().buyRewardItem((int)rewardItem.getId(),1);
    }

    @Override
    public String getName() {
        return "购买"+rewardItem.getName();
    }
}
