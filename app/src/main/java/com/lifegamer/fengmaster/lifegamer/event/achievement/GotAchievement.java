package com.lifegamer.fengmaster.lifegamer.event.achievement;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 获得成就事件
 */

public class GotAchievement {

    private Achievement achievement;

    public GotAchievement(Achievement achievement) {
        this.achievement = achievement;
    }

    public Achievement getAchievement() {
        return achievement;
    }
}
