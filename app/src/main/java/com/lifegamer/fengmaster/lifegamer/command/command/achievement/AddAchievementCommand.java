package com.lifegamer.fengmaster.lifegamer.command.command.achievement;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * Created by qianzise on 2017/10/22.
 */

public class AddAchievementCommand extends AbsCancelableCommand {

    private Achievement achievement;

    public AddAchievementCommand(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public void execute() {
        Game.getInstance().getAchievementManager().addAchievement(achievement);
    }

    @Override
    public void undo() {
        Game.getInstance().getAchievementManager().removeAchievement(achievement.getId());
    }

    @Override
    public String getName() {
        return "添加成就"+achievement.getName();
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
