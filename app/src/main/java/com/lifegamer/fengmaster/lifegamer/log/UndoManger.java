package com.lifegamer.fengmaster.lifegamer.log;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.log.undoHandler.IUndoHandler;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IUndoManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.util.List;

/**
 * Created by FengMaster on 18/12/11.
 */
public class UndoManger implements IUndoManager {
    @Override
    public void undo() {
        int eventSequence = Game.getInstance().getLogManager().getEventSequence();
        undo(eventSequence);
    }

    @Override
    public void undo(int eventSequence) {
        List<Log> eventLogs = Game.getInstance().getLogManager().getEventLogs(eventSequence);
        for (Log eventLog : eventLogs) {
            
            switch (eventLog.getType()){
                case Log.TYPE.SKILL:
                    undoSkill(eventLog);
                    break;
            }
        }

        //撤销后,日志删除
        Stream.of(eventLogs).forEach(log -> Game.getInstance().getLogManager().delteLog(log));
    }

    /**
     * 撤销技能
     * @param eventLog
     */
    private void undoSkill(Log eventLog) {
        String operName = eventLog.getOperName();
        Skill skill = Game.getInstance().getSkillManager().getSkill(operName);
        String property = eventLog.getProperty();

        switch (property){
            case Log.PROPERTY.XP:
                if (eventLog.getAction().equals(Log.ACTION.ADD)){
                    skill.setXP(skill.getXP()-Integer.valueOf(eventLog.getValue()));
                }else if (eventLog.getAction().equals(Log.ACTION.SUB)){
                    skill.setXP(skill.getXP()+Integer.valueOf(eventLog.getValue()));
                }
                break;
            case Log.PROPERTY.LEVEL:

                skill.setLevel(Integer.valueOf(eventLog.getOldValue()));
                break;
            default:
                break;
        }
    }
}
