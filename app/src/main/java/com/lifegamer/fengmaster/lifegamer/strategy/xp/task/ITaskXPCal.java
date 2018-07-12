package com.lifegamer.fengmaster.lifegamer.strategy.xp.task;

/**
 * 任务经验值计算
 * Created by FengMaster on 18/07/13.
 */
public interface ITaskXPCal {

    /**
     * 计算任务应获经验值
     * @param difficulty 困难系数
     * @param fear 恐惧系数
     * @param urgency 紧急系数
     * @return
     */
    int calXP(int difficulty,int fear,int urgency);
}
