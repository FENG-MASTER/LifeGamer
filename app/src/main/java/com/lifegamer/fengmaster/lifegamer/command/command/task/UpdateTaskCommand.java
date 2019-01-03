package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.UpdateTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;

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
    public boolean execute() {
        if (Game.getInstance().getTaskManager().updateTask(task)){
            EventBus.getDefault().post(new UpdateTaskEvent(task));
            return true;
        }else {
            return false;
        }


    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_update)+task.getName();
    }
}
