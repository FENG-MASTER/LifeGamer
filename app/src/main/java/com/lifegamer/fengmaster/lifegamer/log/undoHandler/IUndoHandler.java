package com.lifegamer.fengmaster.lifegamer.log.undoHandler;

import com.lifegamer.fengmaster.lifegamer.model.Log;

/**
 * Created by FengMaster on 18/12/12.
 */
public interface IUndoHandler {

    String getAction();

    String getType();

    String getProperty();

    void handler(Log log);

}
