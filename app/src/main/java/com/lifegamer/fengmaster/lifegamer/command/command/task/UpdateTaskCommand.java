package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by qianzise on 2017/10/19.
 *
 * 更新任务命令
 */

public class UpdateTaskCommand extends AbsNoCancelableCommand {
    private Task task;

    public UpdateTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute() {
        Game.getInstance().getTaskManager().updateTask(task);
    }

    @Override
    public String getName() {
        return "更新任务"+task.getName();
    }
}
