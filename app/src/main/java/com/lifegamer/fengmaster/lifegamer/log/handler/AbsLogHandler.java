package com.lifegamer.fengmaster.lifegamer.log.handler;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by FengMaster on 18/12/06.
 *
 * 日志处理器接口
 *
 */
public abstract class AbsLogHandler {

    public void handle(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);

        Log log=new Log();
        log.setAction(logPoint.action());
        log.setType(logPoint.type());
        log.setProperty(logPoint.property());
        handleDetail(joinPoint,log);
        Game.getInstance().getLogManager().addLog(log);
    }

    protected abstract void handleDetail(JoinPoint joinPoint,Log log);

}
