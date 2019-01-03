package com.lifegamer.fengmaster.lifegamer.command.command;

import com.lifegamer.fengmaster.lifegamer.base.IName;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 命令接口
 */

public interface ICommand {

    /**
     * 执行
     */
    boolean execute();

    /**
     * 撤销
     */
    void undo();

    /**
     * 是否可以撤销
     * @return
     */
    boolean isUndoable();

    /**
     * 显示名称
     * @return 名称
     */
    String getName();

    /**
     * 撤销动作名称
     * @return 撤销名称
     */
    String getUndoActionName();

    /**
     * 是否需要提示
     * @return 是否需要提示
     */
    boolean isShow();

    /**
     * 设置是否需要显示
     * @return 是否显示
     */
    ICommand setShow(boolean show);
}
