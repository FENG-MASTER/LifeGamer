package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.log.handler.AbsLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.HeroLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.SkillLogHandler;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
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
        Cursor query = DBHelper.getInstance().getReadableDatabase().rawQuery("SELECT MAX(eventSequence) FROM"+DBHelper.TABLE_LOG, null);
        if (query==null||query.getCount()==0||!query.moveToNext()){
            return;
        }

    }

    /**
     * 所有注解了log的方法,都会写日志
     */
    @Pointcut("execution(* *(..)) && @annotation(com.lifegamer.fengmaster.lifegamer.log.LogPoint)")
    public void logPointCut(){}

    @Around("logPointCut()")
    public void log(ProceedingJoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);

        AbsLogHandler handler=null;

        switch (logPoint.type()){
            case Log.TYPE.HERO:
                handler=new HeroLogHandler();
                break;
            case Log.TYPE.SKILL:
                handler=new SkillLogHandler();
                break;
            default:
                break;

        }
        handler.beforeHandle(joinPoint);
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        handler.afterHandle(joinPoint);

    }


    @Override
    public void addLog(Log log) {
        Game.insert(log);
    }
}
