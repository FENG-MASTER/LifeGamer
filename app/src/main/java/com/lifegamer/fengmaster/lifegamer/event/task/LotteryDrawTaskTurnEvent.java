package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * 抽奖类型任务转换为一般任务事件
 * Created by FengMaster on 19/10/23.
 */
public class LotteryDrawTaskTurnEvent {

    private Task task;

    public LotteryDrawTaskTurnEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
