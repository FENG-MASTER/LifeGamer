package com.lifegamer.fengmaster.lifegamer.log;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.log.undo.UndoHandler;
import com.lifegamer.fengmaster.lifegamer.log.undo.UndoHandlers;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IUndoManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.util.LogUtil;

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

    public UndoManger(){
        initMap();
    }

    private void initMap() {
        Stream.of(UndoHandlers.class.getMethods()).filter(value -> value.getAnnotation(UndoHandler.class) != null).forEach(method -> {

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
    }

    @Override
    public void undo() {
        int eventSequence = Game.getInstance().getLogManager().getEventSequence();
        undo(eventSequence);
    }

    @Override
    public void undo(int eventSequence) {
        List<Log> eventLogs = Game.getInstance().getLogManager().getEventLogs(eventSequence);

        for (Log eventLog : eventLogs) {
            LogUtil.i("撤销动作 type:"+eventLog.getType()+" action:"+eventLog.getAction()+" property:"+eventLog.getProperty());

            if (methodMap.containsKey(eventLog.getType()) &&
                    methodMap.get(eventLog.getType()).containsKey(eventLog.getAction()) &&
                    methodMap.get(eventLog.getType()).get(eventLog.getAction()).containsKey(eventLog.getProperty())) {
                //有相关的处理函数
                Method method = methodMap.get(eventLog.getType()).get(eventLog.getAction()).get(eventLog.getProperty());
                try {
                    method.invoke(null, new Object[]{eventLog});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
      }



}
