package com.lifegamer.fengmaster.lifegamer.log.handler;

import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.aspectj.lang.JoinPoint;

/**
 * Created by FengMaster on 18/12/10.
 * 技能日志处理器
 */
public class SkillLogHandler extends AbsLogHandler {

    private Skill skill;


    private void xpLogBefore(JoinPoint joinPoint, Log log, Object[] args) {
        log.setValue(String.valueOf((int) args[0]));

        log.setOperName(skill.getName());
        log.setOldValue(String.valueOf(skill.getXP()));
    }

    private void xpLogAfter(JoinPoint joinPoint, Log log, Object[] args) {
        log.setNewValue(String.valueOf(skill.getXP()));
    }

    private void levelLogBefore(JoinPoint joinPoint, Log log, Object[] args) {
        log.setValue(String.valueOf((int) args[0]));
        log.setOldValue(String.valueOf(skill.getLevel()));
    }

    private void levelLogAfter(JoinPoint joinPoint, Log log, Object[] args) {
        log.setNewValue(String.valueOf(skill.getLevel()));
    }

    @Override
    protected void beforeHandleDetail(JoinPoint joinPoint) {
        skill = (Skill) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        switch (log.getProperty()) {
            case Log.PROPERTY.XP:
                //经验
                xpLogBefore(joinPoint, log, args);
                break;
            case Log.PROPERTY.LEVEL:
                //等级
                levelLogBefore(joinPoint, log, args);
                break;
            default:

                break;
        }
    }

    @Override
    protected void afterHandleDetail(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        switch (log.getProperty()) {
            case Log.PROPERTY.XP:
                //经验
                xpLogAfter(joinPoint, log, args);
                break;
            case Log.PROPERTY.LEVEL:
                //等级
                levelLogAfter(joinPoint, log, args);
                break;
            default:

                break;
        }
    }


}
