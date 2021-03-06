package com.lifegamer.fengmaster.lifegamer.command.command.achievement;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import org.greenrobot.eventbus.EventBus;

/**
 * 获得成就指令
 * Created by FengMaster on 18/07/12.
 */
public class GotAchievementCommand extends AbsCancelableCommand {

    private Achievement achievement;

    public GotAchievementCommand(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public boolean execute() {
        boolean b = Game.getInstance().getAchievementManager().gainAchievement(achievement.getId());
        EventBus.getDefault().post(new GotAchievementEvent(achievement));
        return b;
    }

    @Override
    public void undo() {
        super.undo();
        EventBus.getDefault().post(new LostAchievementEvent(achievement));
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.achievement_got)+achievement.getName();
    }


}
