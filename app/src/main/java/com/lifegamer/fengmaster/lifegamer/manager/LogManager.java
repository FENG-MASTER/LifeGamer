package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.log.handler.HeroLogHandler;
import com.lifegamer.fengmaster.lifegamer.log.handler.SkillLogHandler;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ILogManager;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
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

    /**
     * 所有注解了log的方法,都会写日志
     */
    @Pointcut("execution(* *(..)) && @annotation(com.lifegamer.fengmaster.lifegamer.log.LogPoint)")
    public void logPointCut(){}

    @After("logPointCut()")
    public void log(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);
        switch (logPoint.type()){
            case Log.TYPE.HERO:
                HeroLogHandler.getInstance().handle(joinPoint);
                break;
            case Log.TYPE.SKILL:
                SkillLogHandler.getInstance().handle(joinPoint);
                break;

        }

    }


    @Override
    public void addLog(Log log) {
        Game.insert(log);
    }
}
