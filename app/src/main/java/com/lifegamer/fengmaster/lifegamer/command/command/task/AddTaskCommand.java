package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/19.
 *
 * 新建任务命令
 */

public class AddTaskCommand extends AbsCancelableCommand {
    private Task task;

    public AddTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getTaskManager().addTask(task)){
            EventBus.getDefault().post(new NewTaskEvent(task));

        }
    }

    @Override
    public void undo() {
        Game.getInstance().getTaskManager().removeTask((int)task.getId());
    }

    @Override
    public String getName() {
        return "添加任务"+task.getName()+"成功";
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
