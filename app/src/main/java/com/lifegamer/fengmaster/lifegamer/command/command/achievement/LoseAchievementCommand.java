package com.lifegamer.fengmaster.lifegamer.command.command.achievement;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import org.greenrobot.eventbus.EventBus;

/**
 * 失去成就命令
 * Created by FengMaster on 18/07/12.
 */
public class LoseAchievementCommand extends AbsCancelableCommand {

    private Achievement achievement;

    public LoseAchievementCommand(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public void execute() {
        Game.getInstance().getAchievementManager().lostAchievement(achievement.getId());
        EventBus.getDefault().post(new LostAchievementEvent(achievement));

    }

    @Override
    public void undo() {
        Game.getInstance().getAchievementManager().gainAchievement(achievement.getId());
        EventBus.getDefault().post(new GotAchievementEvent(achievement));
    }

    @Override
    public String getName() {
        return "失去成就"+achievement.getName();
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}