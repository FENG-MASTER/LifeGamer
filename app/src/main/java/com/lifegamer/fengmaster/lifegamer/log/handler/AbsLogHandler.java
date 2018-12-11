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
 * 继承本接口的类一般不应该是单例模式
 *
 */
public abstract class AbsLogHandler {

    protected Log log=new Log();

    /**
     * 前置处理
     * @param joinPoint
     */
    public void beforeHandle(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        LogPoint logPoint = ((MethodSignature) signature).getMethod().getAnnotation(LogPoint.class);

        log.setAction(logPoint.action());
        log.setType(logPoint.type());
        log.setProperty(logPoint.property());
        beforeHandleDetail(joinPoint);
    }

    protected abstract void beforeHandleDetail(JoinPoint joinPoint);

    /**
     * 后置处理
     * @param joinPoint
     */
    public void afterHandle(JoinPoint joinPoint){
        afterHandleDetail(joinPoint);

    }

    protected abstract void afterHandleDetail(JoinPoint joinPoint);


    public void setEventSequence(int eventSequence){
        log.setEventSequence(eventSequence);
    }

    public Log getLog() {
        return log;
    }
}
