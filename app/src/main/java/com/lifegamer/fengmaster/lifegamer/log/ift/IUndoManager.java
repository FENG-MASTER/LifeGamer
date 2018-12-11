package com.lifegamer.fengmaster.lifegamer.log.ift;

/**
 * Created by FengMaster on 18/12/11.
 *
 * 撤销管理器
 */
public interface IUndoManager {

    void undo();

    void undo(int eventSequence);

}
