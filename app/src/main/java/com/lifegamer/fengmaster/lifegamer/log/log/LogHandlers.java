package com.lifegamer.fengmaster.lifegamer.log.log;

import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.aspectj.lang.JoinPoint;

/**
 * Created by FengMaster on 18/12/13.
 */
public class LogHandlers {

//    ------------------------------技能相关-------------------------------------

    private static Skill skillLogCommon(JoinPoint joinPoint,Log log){
        Skill skill = (Skill) joinPoint.getTarget();
        log.setOperId(String.valueOf(skill.getId()));
        return skill;
    }

    /**
     * 技能经验日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.BEFORE)
    public static void skillXpLogB(JoinPoint joinPoint,Log log){
        Skill skill = skillLogCommon(joinPoint, log);
        Object[] args = joinPoint.getArgs();
        log.setValue(String.valueOf((int) args[0]));
        log.setOperName(skill.getName());
        log.setOldValue(String.valueOf(skill.getXP()));
    }


    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.AFTER)
    public static void skillXpLogA(JoinPoint joinPoint,Log log){
        Skill skill = (Skill) joinPoint.getTarget();
        log.setNewValue(String.valueOf(skill.getXP()));
    }


    /**
     * 技能等级日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LEVEL,order = Log.ORDER.BEFORE)
    public static void skillLevelLogB(JoinPoint joinPoint,Log log){
        Skill skill = skillLogCommon(joinPoint, log);
        Object[] args = joinPoint.getArgs();
        log.setValue(String.valueOf((int) args[0]));
        log.setOperName(skill.getName());
        log.setOldValue(String.valueOf(skill.getLevel()));
    }


    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LEVEL,order = Log.ORDER.AFTER)
    public static void skillLevelLogA(JoinPoint joinPoint,Log log){
        Skill skill = (Skill) joinPoint.getTarget();
        log.setNewValue(String.valueOf(skill.getLevel()));
    }


//    ------------------------------技能相关-------------------------------------

}
