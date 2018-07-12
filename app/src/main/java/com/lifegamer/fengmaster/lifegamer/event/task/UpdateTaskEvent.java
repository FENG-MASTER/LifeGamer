package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * 任务信息更新事件
 * Created by FengMaster on 18/07/12.
 */
public class UpdateTaskEvent {

    private Task task;

    public UpdateTaskEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
