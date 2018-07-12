package com.lifegamer.fengmaster.lifegamer.event.achievement;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 获得成就事件
 */

public class GotAchievementEvent {

    private Achievement achievement;

    public GotAchievementEvent(Achievement achievement) {
        this.achievement = achievement;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}
