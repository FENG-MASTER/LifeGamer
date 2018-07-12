package com.lifegamer.fengmaster.lifegamer.strategy.xp.task;

/**
 * Created by FengMaster on 18/07/13.
 */
public class BaseTaskXPCal implements ITaskXPCal {
    @Override
    public int calXP(int difficulty, int fear, int urgency) {
        return difficulty+fear+urgency;
    }
}
