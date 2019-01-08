package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * 任务失败事件
 * Created by FengMaster on 19/01/09.
 */
public class FailTaskEvent {

    private Task task;

    public FailTaskEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
