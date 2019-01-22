package com.lifegamer.fengmaster.lifegamer.event.achievement;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * 新增成就事件
 * Created by FengMaster on 19/01/22.
 */
public class NewAchievementEvent {

    private Achievement achievement;

    public NewAchievementEvent(Achievement achievement) {
        this.achievement = achievement;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}
