package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 新增任务事件
 */

public class NewTaskEvent {

    private Task task;

    public NewTaskEvent(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
