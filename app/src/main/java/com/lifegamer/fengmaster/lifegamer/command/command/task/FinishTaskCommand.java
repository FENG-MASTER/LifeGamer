package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by FengMaster on 18/07/12.
 */
public class FinishTaskCommand extends AbsCancelableCommand {

    private Task task;

    public FinishTaskCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute() {
        Game.getInstance().getTaskManager().finishTask(task.getId());
    }

    @Override
    public void undo() {
        Game.getInstance().getUndoManager().undo();
//        Game.getInstance().getTaskManager().undoFinishTask(task.getId());
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_finish)+task.getName();
    }


}
