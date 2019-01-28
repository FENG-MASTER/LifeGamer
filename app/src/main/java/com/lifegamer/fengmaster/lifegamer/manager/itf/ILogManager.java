package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Log;

import java.util.List;

/**
 * Created by FengMaster on 18/12/06.
 *
 *  日志管理器接口
 *
 */
public interface ILogManager {

    /**
     * 获取当前事件序号(即下一次事件的序号)
     * @return
     */
    int getEventSequence();

    void addLog(Log log);

    void updateLog(Log log);

    void deleteLog(Log log);

    /**
     * 获取所有日志
     * @return
     */
    List<Log> getAllLog();

    /**
     * 获取特定类型的日志
     * @param type
     * @return
     */
    List<Log> getAllLog(String type);
    /**
     * 获取指定事件序号的所有日志
     * @param eventSequence
     * @return
     */
    List<Log> getEventLogs(int eventSequence);
}
