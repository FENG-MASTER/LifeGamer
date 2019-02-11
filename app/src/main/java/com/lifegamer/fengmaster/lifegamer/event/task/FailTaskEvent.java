package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.TTSUtil;

/**
 * 任务失败事件
 * Created by FengMaster on 19/01/09.
 */
public class FailTaskEvent {

    private Task task;

    public FailTaskEvent(Task task) {
        this.task = task;
        TTSUtil.getInstance().speak(task.getName()+"任务失败");

    }

    public Task getTask() {
        return task;
    }
}
