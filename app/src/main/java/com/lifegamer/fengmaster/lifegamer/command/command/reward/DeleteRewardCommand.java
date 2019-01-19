package com.lifegamer.fengmaster.lifegamer.command.command.reward;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.reward.DeleteRewardEvent;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import org.greenrobot.eventbus.EventBus;

public class DeleteRewardCommand extends AbsNoCancelableCommand {

    private RewardItem rewardItem;

    public DeleteRewardCommand(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }

    @Override
    public boolean execute() {
        boolean b = Game.getInstance().getRewardManager().removeRewardItem(rewardItem.getId());
        if (b){
            EventBus.getDefault().post(new DeleteRewardEvent(rewardItem));
        }
        return b;
    }

    @Override
    public String getName() {
        return "删除" +rewardItem.getName();
    }
}
