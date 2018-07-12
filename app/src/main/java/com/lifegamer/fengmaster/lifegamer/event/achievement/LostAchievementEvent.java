package com.lifegamer.fengmaster.lifegamer.event.achievement;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 失去成就事件
 */

public class LostAchievementEvent {
    private Achievement achievement;

    public LostAchievementEvent(Achievement achievement) {
        this.achievement = achievement;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}
