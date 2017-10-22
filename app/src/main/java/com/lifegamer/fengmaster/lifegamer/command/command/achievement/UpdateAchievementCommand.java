package com.lifegamer.fengmaster.lifegamer.command.command.achievement;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * Created by qianzise on 2017/10/22.
 */

public class UpdateAchievementCommand extends AbsNoCancelableCommand {

    private Achievement achievement;

    public UpdateAchievementCommand(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public void execute() {
        Game.getInstance().getAchievementManager().updateAchievement(achievement);
    }

    @Override
    public String getName() {
        return "更新成就"+achievement.getName();
    }
}
