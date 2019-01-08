package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * 任务完成事件
 * Created by FengMaster on 19/01/08.
 */
public class FinishTaskEvent {

    private Task task;

    public FinishTaskEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
