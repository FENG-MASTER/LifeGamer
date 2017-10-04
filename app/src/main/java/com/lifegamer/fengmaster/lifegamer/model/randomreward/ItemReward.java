package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 概率型奖励
 */

public class ItemReward extends AbsRandomReward{

    private static Random random;

    private String item;

    /**
     * 物品数量
     */
    private int num;

    /**
     * 获得奖励几率 (千分比)
     */
    private int probability;


    public ItemReward(String item, int num, int probability) {
        this.item = item;
        this.num = num;
        this.probability = probability;
    }

    public String getItem() {
        return item;
    }

    public int getNum() {
        return num;
    }


    @Override
    public int getProbability() {
        return probability;
    }
}
