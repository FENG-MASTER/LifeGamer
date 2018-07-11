package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.DeleteTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;

/**
 * 删除任务指令
 * Created by FengMaster on 18/07/11.
 */
public class DeleteTaskCommend extends AbsNoCancelableCommand {

    private Task task;

    public DeleteTaskCommend(Task task) {
        this.task = task;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getTaskManager().removeTask((int) task.getId())){
            EventBus.getDefault().post(new DeleteTaskEvent((int) task.getId()));
        }

    }

    @Override
    public String getName() {
        return "删除任务"+task.getName()+"成功!";
    }
}
