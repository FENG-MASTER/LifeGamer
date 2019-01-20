package com.lifegamer.fengmaster.lifegamer.command.command.achievement;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * 删除成就 指令
 */
public class DeleteAchievementCommand extends AbsNoCancelableCommand {
    private Achievement achievement;

    public DeleteAchievementCommand(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getAchievementManager().removeAchievement(achievement.getId());
    }

    @Override
    public String getName() {
        return "删除" +achievement.getName();
    }
}
