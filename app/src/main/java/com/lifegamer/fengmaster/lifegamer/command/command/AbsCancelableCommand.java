package com.lifegamer.fengmaster.lifegamer.command.command;

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
}
