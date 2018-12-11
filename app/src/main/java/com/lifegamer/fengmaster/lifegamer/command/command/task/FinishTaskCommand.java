package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.lifegamer.fengmaster.lifegamer.Game;
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
        return "完成任务"+task.getName();
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
