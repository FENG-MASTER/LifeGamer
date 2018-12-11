package com.lifegamer.fengmaster.lifegamer.log;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.log.handler.AbsLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.HeroLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.SkillLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.TaskLogHandler;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by FengMaster on 18/12/06.
 *
 * 日志管理器
 */
@Aspect
public class LogManager implements ILogManager {


    private static AtomicInteger eventSequence=new AtomicInteger(0);


    public LogManager() {
        if (eventSequence.get()==0){
            getEventSequenceFromDb();
        }
    }


    private void getEventSequenceFromDb(){
        Cursor query = DBHelper.getInstance().getReadableDatabase().query(DBHelper.TABLE_LOG,null,null, null,null,null,"_id desc","1");
        if (query!=null&&query.moveToNext()){
            eventSequence.set(query.getInt(query.getColumnIndex("eventSequence")));
        }else {
            //空记录则从1开始
            eventSequence.set(0);
        }
        android.util.Log.e("qianzise","初始化 "+String.valueOf(eventSequence));
    }

    /**
     * 所有注解了log的方法,都会写日志
     */
//    @Pointcut("execution(* *(..)) && @annotation(com.lifegamer.fengmaster.lifegamer.log.LogPoint)")
    @Pointcut("call(@com.lifegamer.fengmaster.lifegamer.log.LogPoint * *(..))")
    public void logPointCut(){}


    @Around("logPointCut()")
    public Object log(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);

        AbsLogHandler handler=null;

        //选择相应的日志处理器
        switch (logPoint.type()){
            case Log.TYPE.HERO:
                handler=new HeroLogHandler();
                break;
            case Log.TYPE.SKILL:
                handler=new SkillLogHandler();
                break;
            case Log.TYPE.TASK:
                handler=new TaskLogHandler();
                break;
            default:
                break;

        }

        if (Game.getInstance().getCommandManager().isLastestCommandIsHead()){
            //一组新的相关日志组,eventSequence+1
            eventSequence.addAndGet(1);
            android.util.Log.e("qianzise",String.valueOf(eventSequence));
        }
        //添加事件序列号,用于确定一系列日志归属与同一事件
        handler.setEventSequence(eventSequence.get());
        //方法执行前
        handler.beforeHandle(joinPoint);
        Game.getInstance().getLogManager().addLog(handler.getLog());

        Object o = ((ProceedingJoinPoint) joinPoint).proceed();

        //方法执行后
        handler.afterHandle(joinPoint);
        Game.getInstance().getLogManager().updateLog(handler.getLog());

        return o;
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
        ArrayList<Log> logs=new ArrayList<>();
        while (query.moveToNext()){
            Log log = new Log();
            log.getFromCursor(query);
            logs.add(log);
        }
        return logs;
    }


}
