package com.lifegamer.fengmaster.lifegamer.event.task;

/**
 * 删除任务事件
 * Created by FengMaster on 18/07/11.
 */
public class DeleteTaskEvent {

    private int taskID;

    public DeleteTaskEvent(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return taskID;
    }
}
