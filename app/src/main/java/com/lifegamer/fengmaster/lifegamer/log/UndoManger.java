package com.lifegamer.fengmaster.lifegamer.log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IUndoManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by FengMaster on 18/12/11.
 */
public class UndoManger implements IUndoManager {
    private Map<String, Map<String, Map<String, Method>>> methodMap = new HashMap<>();

    @Override
    public void undo() {
        int eventSequence = Game.getInstance().getLogManager().getEventSequence();
        undo(eventSequence);
    }

    @Override
    public void undo(int eventSequence) {
        List<Log> eventLogs = Game.getInstance().getLogManager().getEventLogs(eventSequence);

        Stream.of(this.getClass().getMethods()).filter(value -> value.getAnnotation(UndoHandler.class) != null).forEach(method -> {

            UndoHandler undoHandler = method.getAnnotation(UndoHandler.class);
            if (!methodMap.containsKey(undoHandler.type())) {
                methodMap.put(undoHandler.type(), new HashMap<>());
            }

            Map<String, Map<String, Method>> map1 = methodMap.get(undoHandler.type());
            if (!map1.containsKey(undoHandler.action())) {
                map1.put(undoHandler.action(), new HashMap<>());
            }

            Map<String, Method> map2 = methodMap.get(undoHandler.type()).get(undoHandler.action());

            if (!map2.containsKey(undoHandler.property())) {
                map2.put(undoHandler.property(), method);
            }


        });
        for (Log eventLog : eventLogs) {
            if (methodMap.containsKey(eventLog.getType()) &&
                    methodMap.get(eventLog.getType()).containsKey(eventLog.getAction()) &&
                    methodMap.get(eventLog.getType()).get(eventLog.getAction()).containsKey(eventLog.getProperty())) {
                //有相关的处理函数
                Method method = methodMap.get(eventLog.getType()).get(eventLog.getAction()).get(eventLog.getProperty());
                try {
                    method.invoke(this,new Object[]{eventLog});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }


        }

        //撤销后,日志删除
        Stream.of(eventLogs).forEach(log -> Game.getInstance().getLogManager().delteLog(log));
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.ADD, property = Log.PROPERTY.XP)
    public void skillXpAddUndo(Log log) {
        String operName = log.getOperName();
        Skill skill = Game.getInstance().getSkillManager().getSkill(operName);
        skill.setXP(skill.getXP() - Integer.valueOf(log.getValue()));
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.SUB, property = Log.PROPERTY.XP)
    public void skillXpSubUndo(Log log) {
        String operName = log.getOperName();
        Skill skill = Game.getInstance().getSkillManager().getSkill(operName);
        skill.setXP(skill.getXP() + Integer.valueOf(log.getValue()));
        Game.getInstance().getSkillManager().updateSkill(skill);
    }
}
