package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 * <p>
 * 概率型物品奖励
 */

public class RandomItemReward extends AbsRandomReward {

    private long rewardID;

    /**
     * 物品数量
     */
    private int num;

    /**
     * 获得奖励几率 (千分比)
     */
    private int probability;


    public RandomItemReward(long rewardID, int num, int probability) {
        this.rewardID = rewardID;
        this.num = num;
        this.probability = probability;
    }


    public long getRewardID() {
        return rewardID;
    }

    public void setRewardID(long rewardID) {
        this.rewardID = rewardID;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    @Override
    public boolean equals(Object obj) {
        return ((RandomItemReward)obj).rewardID==rewardID;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(rewardID).hashCode();
    }
}
