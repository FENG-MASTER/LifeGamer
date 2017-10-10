package com.lifegamer.fengmaster.lifegamer.strategy.xp;

/**
 * Created by qianzise on 2017/10/10.
 *
 * 普通经验成长曲线算法
 */

public class NormalLevelXP implements ILevelXP {
    @Override
    public int getXP(int level) {
        return level*1000;
    }
}
