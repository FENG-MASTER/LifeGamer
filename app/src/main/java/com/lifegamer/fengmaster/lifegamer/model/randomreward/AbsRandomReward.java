package com.lifegamer.fengmaster.lifegamer.model.randomreward;

import java.util.Random;

/**
 * Created by qianzise on 2017/10/5.
 *
 * 概率型奖励抽象类
 */

public abstract class AbsRandomReward {

    private static Random random=new Random();

    /**
     * 是否抽中
     */
    private boolean hitFlag=false;

    /**
     * 是否已经抽过
     */
    private boolean hasHitted=false;

    public abstract int getProbability();

    public boolean isHit(){
        if (hasHitted){
            //如果已经抽过了,返回之前结果
            return hitFlag;
        }else {
            hasHitted=true;
            hitFlag=random.nextInt(1000)<=getProbability();
            return hitFlag;
        }
    }

}
