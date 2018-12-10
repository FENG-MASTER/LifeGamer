package com.lifegamer.fengmaster.lifegamer.log.handler;

import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by FengMaster on 18/12/10.
 */
public class SkillLogHandler extends AbsLogHandler {
    private static final SkillLogHandler ourInstance = new SkillLogHandler();

    public static SkillLogHandler getInstance() {
        return ourInstance;
    }

    private SkillLogHandler() {
    }


    @Override
    protected void handleDetail(JoinPoint joinPoint, Log log) {
        Object[] args = joinPoint.getArgs();
        switch (log.getProperty()){
            case  Log.PROPERTY.XP:
                //经验
                xpLog(log,args);
                break;
            case Log.PROPERTY.LEVEL:
                //等级
                levelLog(log,args);
                break;
            default:

                break;
        }



    }


    private void xpLog(Log log,Object[] args){
        log.setValue(String.valueOf((int)args[0]));

    }

    private void levelLog(Log log,Object[] args){

    }
}
