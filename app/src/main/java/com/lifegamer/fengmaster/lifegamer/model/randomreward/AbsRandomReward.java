package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 */

public abstract class AbsRandomReward {

    private static Random random=new Random();

    private boolean hitFlag=false;

    public abstract int getProbability();

    public boolean isHit(){
        if (hitFlag){
            //如果已经抽过了
            return false;
        }
        hitFlag=true;
        return random.nextInt(1000)<=getProbability();
    }

}
