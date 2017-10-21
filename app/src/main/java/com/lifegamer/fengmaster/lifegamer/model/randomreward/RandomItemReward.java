package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 概率型物品奖励
 */

public class RandomItemReward extends AbsRandomReward{

    private static Random random;

    private String rewardName;

    /**
     * 物品数量
     */
    private int num;

    /**
     * 获得奖励几率 (千分比)
     */
    private int probability;


    public RandomItemReward(String rewardName, int num, int probability) {
        this.rewardName = rewardName;
        this.num = num;
        this.probability = probability;
    }

    public String getRewardName() {
        return rewardName;
    }

    public int getNum() {
        return num;
    }


    @Override
    public int getProbability() {
        return probability;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}
