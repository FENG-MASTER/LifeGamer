package com.lifegamer.fengmaster.lifegamer.model.randomreward;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 概率型成就奖励
 */

public class AchievementReward extends AbsRandomReward {
    private long achievementID;

    private int probability;

    public AchievementReward(long achievementID, int probability) {
        this.achievementID = achievementID;
        this.probability = probability;
    }

    @Override
    public int getProbability() {
        return probability;
    }

    public long getAchievementID() {
        return achievementID;
    }

    public void setAchievementID(long achievementID) {
        this.achievementID = achievementID;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(Object o) {
        return achievementID==(((AchievementReward)o).getAchievementID());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(achievementID).hashCode();
    }
}
