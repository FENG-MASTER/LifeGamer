package com.lifegamer.fengmaster.lifegamer.command.command;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 不可取消命令
 */

public abstract class AbsNoCancelableCommand implements ICommand{

    protected boolean show=true;

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public String getUndoActionName() {
        return "";
    }

    @Override
    public boolean isShow() {
        return show;
    }

    public AbsNoCancelableCommand setShow(boolean show) {
        this.show = show;
        return this;
    }
}
