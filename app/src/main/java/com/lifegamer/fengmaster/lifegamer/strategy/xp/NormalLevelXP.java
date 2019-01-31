package com.lifegamer.fengmaster.lifegamer.strategy.xp;

/**
 * Created by qianzise on 2017/10/10.
 *
 * 普通经验成长曲线算法
 */

public class NormalLevelXP implements ILevelXP {
    @Override
    public int getXP(int level) {
        if (level<=0){
            return level*-1*1000;
        }else {
            return level*1000;
        }
    }
}
