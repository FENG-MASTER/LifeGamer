package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.task.EachTimeTaskEndInfo;

public class FailTaskCommand extends AbsCancelableCommand {
    private Task task;
    private EachTimeTaskEndInfo eachTimeTaskEndInfo;

    public FailTaskCommand(Task task, EachTimeTaskEndInfo eachTimeTaskEndInfo) {
        this.task = task;
        this.eachTimeTaskEndInfo=eachTimeTaskEndInfo;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getTaskManager().failTaskWithExtInfo(task.getId(),eachTimeTaskEndInfo);
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
