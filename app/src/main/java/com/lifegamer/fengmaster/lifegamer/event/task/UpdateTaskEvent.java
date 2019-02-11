package com.lifegamer.fengmaster.lifegamer.event.task;

import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.TTSUtil;

/**
 * 任务信息更新事件
 * Created by FengMaster on 18/07/12.
 */
public class UpdateTaskEvent {

    private Task task;

    public UpdateTaskEvent(Task task) {
        this.task = task;
        TTSUtil.getInstance().speak(task.getName()+"任务内容更新");
    }

    public Task getTask() {
        return task;
    }
}
