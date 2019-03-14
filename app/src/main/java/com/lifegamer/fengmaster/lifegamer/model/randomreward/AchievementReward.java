package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Created by qianzise on 2017/10/5.
 * <p>
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

    @SuppressFBWarnings("BC_EQUALS_METHOD_SHOULD_WORK_FOR_ALL_OBJECTS")
    @Override
    public boolean equals(Object o) {
        return o != null && achievementID == (((AchievementReward) o).getAchievementID());
    }

    @Override
    public int hashCode() {
        return Long.valueOf(achievementID).hashCode();
    }
}
