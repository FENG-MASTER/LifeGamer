package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.DeleteTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;

/**
 * 删除任务指令
 * Created by FengMaster on 18/07/11.
 */
public class DeleteTaskCommend extends AbsCancelableCommand {

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
    public void undo() {
        Game.getInstance().getTaskManager().addTask(task);
        EventBus.getDefault().post(new NewTaskEvent(task));
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_del)+task.getName()+App.getContext().getString(R.string.success);
    }


}
