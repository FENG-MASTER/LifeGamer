package com.lifegamer.fengmaster.lifegamer.log.handler;

import com.lifegamer.fengmaster.lifegamer.model.Log;

import org.aspectj.lang.JoinPoint;

/**
 * Created by FengMaster on 18/12/06.
 */
public class HeroLogHandler extends AbsLogHandler {

    private static final HeroLogHandler ourInstance = new HeroLogHandler();

    public static HeroLogHandler getInstance() {
        return ourInstance;
    }



    @Override
    protected void handleDetail(JoinPoint joinPoint, Log log) {
        joinPoint.getSignature().getName();

    }
}
