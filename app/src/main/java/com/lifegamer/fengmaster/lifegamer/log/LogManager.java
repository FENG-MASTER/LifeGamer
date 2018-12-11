package com.lifegamer.fengmaster.lifegamer.log;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.log.handler.AbsLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.HeroLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.SkillLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.TaskLogHandler;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by FengMaster on 18/12/06.
 *
 * 日志管理器
 */
@Aspect
public class LogManager implements ILogManager {


    private int eventSequence=0;


    public LogManager() {
        getEventSequenceFromDb();
    }


    private void getEventSequenceFromDb(){
        Cursor query = DBHelper.getInstance().getReadableDatabase().query(DBHelper.TABLE_LOG,null,null, null,null,null,"_id","1");
        if (query!=null&&query.moveToNext()){
            eventSequence=query.getInt(query.getColumnIndex("eventSequence"));
        }else {
            //空记录则从1开始
            eventSequence=1;
        }

    }

    /**
     * 所有注解了log的方法,都会写日志
     */
//    @Pointcut("execution(* *(..)) && @annotation(com.lifegamer.fengmaster.lifegamer.log.LogPoint)")
    @Pointcut("call(@com.lifegamer.fengmaster.lifegamer.log.LogPoint * *(..))")
    public void logPointCut(){}

    @Around("execution(private * * _finishTask(..))")
    public void xxxx(ProceedingJoinPoint joinPoint) throws Throwable {
        android.util.Log.d("ss","ssssssssss");
        joinPoint.proceed();
    }

    @Around("logPointCut()")
    public void log(JoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);
        android.util.Log.d("ss",signature.toLongString());

        AbsLogHandler handler=null;

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
            eventSequence++;
        }
        handler.setEventSequence(eventSequence);
        handler.beforeHandle(joinPoint);

        ((ProceedingJoinPoint)joinPoint).proceed();


        handler.afterHandle(joinPoint);

    }


    @Override
    public void addLog(Log log) {
        Game.insert(log);
    }
}
