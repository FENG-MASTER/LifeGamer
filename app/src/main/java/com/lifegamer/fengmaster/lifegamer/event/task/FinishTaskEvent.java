package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.TTSUtil;

/**
 * 任务完成事件
 * Created by FengMaster on 19/01/08.
 */
public class FinishTaskEvent {

    private Task task;

    public FinishTaskEvent(Task task) {
        this.task = task;
        TTSUtil.getInstance().speak(task.getName()+"任务完成");
    }

    public Task getTask() {
        return task;
    }
}
