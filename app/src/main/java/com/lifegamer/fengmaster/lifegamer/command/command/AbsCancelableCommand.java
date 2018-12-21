package com.lifegamer.fengmaster.lifegamer.command.command;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 可取消命令
 */

public abstract class AbsCancelableCommand implements ICommand {

    protected boolean show=true;

    public AbsCancelableCommand setShow(boolean show) {
        this.show = show;
        return this;
    }

    @Override
    public boolean isUndoable() {
        return true;
    }

    @Override
    public boolean isShow() {
        return true;
    }


    @Override
    public void undo() {
        //默认调用日志的undo功能
        Game.getInstance().getUndoManager().undo();
    }

    @Override
    public String getUndoActionName() {
        return App.getContext().getString(R.string.undo);
    }
}
