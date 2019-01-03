package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Task;

public class FailTaskCommand extends AbsCancelableCommand {
    private Task task;

    public FailTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getTaskManager().failTask(task.getId());
    }

    @Override
    public void undo() {
        Game.getInstance().getTaskManager().undoFailTask(task.getName());
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task)+task.getName()+App.getContext().getString(R.string.fail);
    }


}
