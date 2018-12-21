package com.lifegamer.fengmaster.lifegamer.log;

import android.database.Cursor;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.CommandExec;
import com.lifegamer.fengmaster.lifegamer.log.log.LogHandler;
import com.lifegamer.fengmaster.lifegamer.log.log.LogHandlers;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by FengMaster on 18/12/06.
 * <p>
 * 日志管理器
 */
@Aspect
public class LogManager implements ILogManager {

    private static AtomicInteger eventSequence = new AtomicInteger(0);

    public LogManager() {
        if (eventSequence.get() == 0) {
            getEventSequenceFromDb();
        }
        initMap();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void setEventSequenceAdd(CommandExec commandExec) {
        //一组新的相关日志组,eventSequence+1
        eventSequence.addAndGet(1);
    }


    private Map<String, Map<String, Map<String, Map<String, Method>>>> methodMap = new HashMap<>();

    private void initMap() {
        Stream.of(LogHandlers.class.getMethods()).filter(value -> value.getAnnotation(LogHandler.class) != null).forEach(method -> {

            LogHandler logHandler = method.getAnnotation(LogHandler.class);
            if (!methodMap.containsKey(logHandler.type())) {
                methodMap.put(logHandler.type(), new HashMap<>());
            }

            Map<String, Map<String, Map<String, Method>>> map1 = methodMap.get(logHandler.type());

            for (String action : logHandler.action()) {

                if (!map1.containsKey(action)) {
                    map1.put(action, new HashMap<>());
                }

                Map<String, Map<String, Method>> map2 = methodMap.get(logHandler.type()).get(action);

                if (!map2.containsKey(logHandler.property())) {
                    map2.put(logHandler.property(), new HashMap<>());
                }
                Map<String, Method> map3 = methodMap.get(logHandler.type()).get(action).get(logHandler.property());

                if (!map3.containsKey(logHandler.order())) {
                    map3.put(logHandler.order(), method);
                }


            }


        });
    }


    private void getEventSequenceFromDb() {
        Cursor query = DBHelper.getInstance().getReadableDatabase().query(DBHelper.TABLE_LOG, null, null, null, null, null, "_id desc", "1");
        if (query != null && query.moveToNext()) {
            eventSequence.set(query.getInt(query.getColumnIndex("eventSequence")));
        } else {
            //空记录则从1开始
            eventSequence.set(0);
        }
    }

    /**
     * 所有注解了log的方法,都会写日志
     */
//    @Pointcut("execution(* *(..)) && @annotation(com.lifegamer.fengmaster.lifegamer.log.LogPoint)")
    @Pointcut("call(@com.lifegamer.fengmaster.lifegamer.log.LogPoint * *(..))")
    public void logPointCut() {
    }


    @Around("logPointCut()")
    public Object log(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);

        Log log = new Log();


        //添加事件序列号,用于确定一系列日志归属与同一事件
        log.setEventSequence(eventSequence.get());

        log.setAction(logPoint.action());
        log.setType(logPoint.type());
        log.setProperty(logPoint.property());

        if (methodMap.containsKey(logPoint.type()) &&
                methodMap.get(logPoint.type()).containsKey(logPoint.action()) &&
                methodMap.get(logPoint.type()).get(logPoint.action()).containsKey(logPoint.property())) {
            //有相关的处理函数
            Map<String, Method> map = methodMap.get(logPoint.type()).get(logPoint.action()).get(logPoint.property());


            if (map.containsKey(Log.ORDER.BEFORE)) {
                //如果有前置执行函数
                Method method = map.get(Log.ORDER.BEFORE);

                try {
                    method.invoke(null, new Object[]{joinPoint, log});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }


            Game.getInstance().getLogManager().addLog(log);

            Object o = ((ProceedingJoinPoint) joinPoint).proceed();

            if (map.containsKey(Log.ORDER.AFTER)) {
                //如果有后置执行函数
                Method method = map.get(Log.ORDER.AFTER);
                try {
                    method.invoke(null, new Object[]{joinPoint, log});
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                Game.getInstance().getLogManager().updateLog(log);

            }

            return o;

        } else {
            //没有相应的log处理器
            android.util.Log.e(this.getClass().getSimpleName(), "没有相应的日志处理器");
            Game.getInstance().getLogManager().addLog(log);
            Object o = ((ProceedingJoinPoint) joinPoint).proceed();
            return o;

        }


    }


    @Override
    public int getEventSequence() {
        return eventSequence.get();
    }

    @Override
    public void addLog(Log log) {
        long l = Game.insert(log);
        log.setId(l);
    }

    @Override
    public void updateLog(Log log) {
        Game.update(log);
    }

    @Override
    public void delteLog(Log log) {
        Game.delete(log);
    }

    @Override
    public List<Log> getEventLogs(int eventSequence) {
        DBHelper dbHelper = DBHelper.getInstance();
        Cursor query = dbHelper.getReadableDatabase().query(DBHelper.TABLE_LOG, null, "eventSequence=?", new String[]{String.valueOf(eventSequence)}, null, null, "_id");
        ArrayList<Log> logs = new ArrayList<>();
        while (query.moveToNext()) {
            Log log = new Log();
            log.getFromCursor(query);
            logs.add(log);
        }
        return logs;
    }


}
