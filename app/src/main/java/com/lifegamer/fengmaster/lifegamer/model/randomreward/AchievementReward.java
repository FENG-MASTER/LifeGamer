package com.lifegamer.fengmaster.lifegamer.model.randomreward;

/**
 * Created by qianzise on 2017/10/5.
 */

public class AchievementReward extends AbsRandomReward {
    private String achievement;

    private int probability;

    public AchievementReward(String achievement, int probability) {
        this.achievement = achievement;
        this.probability = probability;
    }

    @Override
    public int getProbability() {
        return probability;
    }

    public String getAchievement() {
        return achievement;
    }
}
