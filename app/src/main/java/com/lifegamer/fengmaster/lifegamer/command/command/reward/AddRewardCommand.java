package com.lifegamer.fengmaster.lifegamer.command.command.reward;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.reward.NewRewardEvent;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 新增奖励命令
 */

public class AddRewardCommand extends AbsCancelableCommand {

    private RewardItem rewardItem;

    public AddRewardCommand(RewardItem rewardItem) {
        this.rewardItem = rewardItem;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getRewardManager().addRewardItem(rewardItem)){
            EventBus.getDefault().post(new NewRewardEvent(rewardItem));
        }

    }

    @Override
    public void undo() {
        Game.getInstance().getRewardManager().removeRewardItem((int) rewardItem.getId());
    }

    @Override
    public String getName() {
        return "新增奖励"+rewardItem.getName();
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
