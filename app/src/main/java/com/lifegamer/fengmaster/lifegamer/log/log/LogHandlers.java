package com.lifegamer.fengmaster.lifegamer.log.log;

import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.base.IdAble;
import com.lifegamer.fengmaster.lifegamer.model.task.EachTimeTaskEndInfo;

import org.aspectj.lang.JoinPoint;


/**
 * Created by FengMaster on 18/12/13.
 */
public class LogHandlers {


    /**
     * 通用前置对象日志记录方法
     * @param tClass
     * @param joinPoint
     * @param log
     * @param nameFunction
     * @param valueFunction
     * @param <T>
     */
    private static<T extends IdAble> T commonObjLogB(Class<T> tClass, JoinPoint joinPoint, Log log,
                                                        Function<T,String> nameFunction, Function<T,String> valueFunction){
        T t = (T) joinPoint.getTarget();
        log.setOperId(String.valueOf(t.getId()));
        Object[] args = joinPoint.getArgs();
        log.setValue(String.valueOf((int) args[0]));
        log.setOperName(nameFunction.apply(t));
        log.setOldValue(valueFunction.apply(t));

        return t;
    }


    /**
     * 通用后置对象日志方法
     * @param tClass
     * @param joinPoint
     * @param log
     * @param valueFunction
     * @param <T>
     */
    private static<T> T commonObjLogA(Class<T> tClass, JoinPoint joinPoint, Log log, Function<T,String> valueFunction){
        T t = (T) joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();
        log.setNewValue(valueFunction.apply(t));
        return t;
    }


    /**
     * 通用前置方法日志函数
     * @param tClass
     * @param joinPoint
     * @param log
     * @param nameFunction
     * @param valueFunction
     * @param <T>
     */
    private static<T extends IdAble> T commonFuncLogB(Class<T> tClass, JoinPoint joinPoint, Log log,
                                                        Function<T,String> nameFunction, Function<T,String> valueFunction){
        Object[] args = joinPoint.getArgs();
        T t = (T) args[0];//方法日志中,操作对象是输入参数
        log.setOperId(String.valueOf(t.getId()));

        if (args.length>=2){
            log.setValue(String.valueOf(args[1]));
        }

        log.setOperName(nameFunction.apply(t));
        log.setOldValue(valueFunction.apply(t));
        return t;
    }


    /**
     * 通用后置函数日志方法
     * @param tClass
     * @param joinPoint
     * @param log
     * @param valueFunction
     * @param <T>
     */
    private static<T> T commonFuncLogA(Class<T> tClass, JoinPoint joinPoint, Log log, Function<T,String> valueFunction){
        Object[] args = joinPoint.getArgs();
        T t = (T) args[0];
        log.setNewValue(valueFunction.apply(t));
        return t;
    }

//    ------------------------------能力相关-------------------------------------


    /**
     * 删除能力日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void skillDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Skill.class, joinPoint, log, skill -> skill.getName(), skill -> JSONObject.toJSONString(skill));
    }


    /**
     * 新增能力日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void skillCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(Skill.class, joinPoint, log, skill -> skill.getName(), skill -> JSONObject.toJSONString(skill));
    }



    /**
     * 编辑能力日志(编辑前
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void skillEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Skill.class, joinPoint, log, skill -> skill.getName(), skill -> JSONObject.toJSONString(skill));
    }


    /**
     * 能力经验日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.BEFORE)
    public static void skillXpLogB(JoinPoint joinPoint,Log log){
        commonObjLogB(Skill.class, joinPoint, log, skill -> skill.getName(), skill -> String.valueOf(skill.getXP()));
    }


    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.AFTER)
    public static void skillXpLogA(JoinPoint joinPoint,Log log){
        commonObjLogA(Skill.class, joinPoint, log, skill -> String.valueOf(skill.getXP()));
    }


    /**
     * 能力等级日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LEVEL,order = Log.ORDER.BEFORE)
    public static void skillLevelLogB(JoinPoint joinPoint,Log log){
        commonObjLogB(Skill.class, joinPoint, log, skill -> skill.getName(), skill -> String.valueOf(skill.getLevel()));
    }


    @LogHandler(type = Log.TYPE.SKILL,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LEVEL,order = Log.ORDER.AFTER)
    public static void skillLevelLogA(JoinPoint joinPoint,Log log){
        commonObjLogA(Skill.class, joinPoint, log, skill -> String.valueOf(skill.getLevel()));
    }


//    ------------------------------能力相关-------------------------------------

//    ------------------------------英雄相关-------------------------------------

    /**
     * 金币日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.HERO,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LIFEPOINT,order = Log.ORDER.BEFORE)
    public static void heroLifepointLogB(JoinPoint joinPoint,Log log){
        LifePoint lifePoint=Game.getInstance().getHeroManager().getHero().getLifePoint();
        Object[] args = joinPoint.getArgs();
        log.setValue(String.valueOf((int) args[0]));
        log.setOperName(Log.PROPERTY.LIFEPOINT);
        log.setOldValue(String.valueOf(lifePoint.getLpPoint()));
    }


    @LogHandler(type = Log.TYPE.HERO,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.LIFEPOINT,order = Log.ORDER.AFTER)
    public static void heroLifepointLogA(JoinPoint joinPoint,Log log){
        LifePoint lifePoint=Game.getInstance().getHeroManager().getHero().getLifePoint();
        log.setNewValue(String.valueOf(lifePoint.getLpPoint()));
    }

    /**
     * 经验日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.HERO,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.BEFORE)
    public static void heroXpLogB(JoinPoint joinPoint,Log log){
        Hero hero = Game.getInstance().getHeroManager().getHero();
        Object[] args = joinPoint.getArgs();
        log.setValue(String.valueOf((int) args[0]));
        log.setOperName(Log.PROPERTY.XP);
        log.setOldValue(String.valueOf(hero.getXp()));
    }


    @LogHandler(type = Log.TYPE.HERO,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.XP,order = Log.ORDER.AFTER)
    public static void heroXpLogA(JoinPoint joinPoint,Log log){
        Hero hero = Game.getInstance().getHeroManager().getHero();
        log.setNewValue(String.valueOf(hero.getXp()));
    }




//    ------------------------------英雄相关-------------------------------------

//    ------------------------------任务相关-------------------------------------


    /**
     * 任务日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TASK,action = {Log.ACTION.FINISH,Log.ACTION.FAIL},property = Log.PROPERTY.TASK,order = Log.ORDER.BEFORE)
    public static void taskLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Task.class, joinPoint, log, task -> task.getName(), task -> String.valueOf(task.getCompleteTimes()));
    }


    @LogHandler(type = Log.TYPE.TASK,action = {Log.ACTION.FINISH,Log.ACTION.FAIL},property = Log.PROPERTY.TASK,order = Log.ORDER.AFTER)
    public static void taskLogA(JoinPoint joinPoint,Log log){
        commonFuncLogA(Task.class, joinPoint, log, task -> String.valueOf(task.getCompleteTimes()));
        Object[] args = joinPoint.getArgs();
        if (args[1]!=null){
            EachTimeTaskEndInfo timeTaskEndInfo= (EachTimeTaskEndInfo) args[1];
            log.setDesc(timeTaskEndInfo.getDesc());
        }
    }


    /**
     * 删除任务日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TASK,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void taskDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Task.class, joinPoint, log, task -> task.getName(), task1 -> JSONObject.toJSONString(task1));
    }


    /**
     * 新增任务日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TASK,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void taskCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(Task.class, joinPoint, log, task -> task.getName(), task1 -> JSONObject.toJSONString(task1));
    }



    /**
     * 编辑任务日志(编辑前
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TASK,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void taskEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Task.class, joinPoint, log, task -> task.getName(), task1 -> JSONObject.toJSONString(task1));
    }





//    ------------------------------任务相关-------------------------------------

//    ------------------------------触发器相关-------------------------------------

    /**
     * 删除触发器日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TRIGGER,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void triggerDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(TriggerInfo.class, joinPoint, log, triggerInfo -> Long.toString(triggerInfo.getId()), triggerInfo1 -> JSONObject.toJSONString(triggerInfo1));
    }


    /**
     * 新增触发器日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TRIGGER,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void triggerCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(TriggerInfo.class, joinPoint, log, triggerInfo -> Long.toString(triggerInfo.getId()), triggerInfo1 -> JSONObject.toJSONString(triggerInfo1));
    }



    /**
     * 编辑触发器日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.TRIGGER,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void triggerEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(TriggerInfo.class, joinPoint, log, triggerInfo -> Long.toString(triggerInfo.getId()), triggerInfo1 -> JSONObject.toJSONString(triggerInfo1));
    }




//    ------------------------------触发器相关-------------------------------------


//    ------------------------------物品相关-------------------------------------


    /**
     * 删除商店物品日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.REWARDITEM,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void rewardItemDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(RewardItem.class, joinPoint, log, rewardItem -> rewardItem.getName(), item -> JSONObject.toJSONString(item));
    }


    /**
     * 新增商店物品日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.REWARDITEM,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void rewardItemCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(RewardItem.class, joinPoint, log, rewardItem -> rewardItem.getName(), item -> JSONObject.toJSONString(item));
    }



    /**
     * 编辑商店物品日志(编辑前
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.REWARDITEM,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void rewardItemBuyLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(RewardItem.class, joinPoint, log, rewardItem -> rewardItem.getName(), item -> JSONObject.toJSONString(item));
    }


    /**
     * 购买商店物品日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.REWARDITEM,action = Log.ACTION.BUY,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void rewardItemEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(RewardItem.class, joinPoint, log, rewardItem -> rewardItem.getName(), item -> JSONObject.toJSONString(item));
    }

    /**
     * 购买商店物品日志(后
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.REWARDITEM,action = Log.ACTION.BUY,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void rewardItemEditLogA(JoinPoint joinPoint,Log log){
        commonFuncLogA(RewardItem.class, joinPoint, log, item -> JSONObject.toJSONString(item));
    }


    /**
     * 删除日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ITEM,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void itemDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Item.class, joinPoint, log, item -> item.getName(), item -> JSONObject.toJSONString(item));
    }


    /**
     * 新增物品日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ITEM,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void itemCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(Item.class, joinPoint, log, item -> item.getName(), item -> JSONObject.toJSONString(item));
    }



    /**
     * 编辑物品日志(编辑前
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ITEM,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void itemEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Item.class, joinPoint, log, item -> item.getName(), item -> JSONObject.toJSONString(item));
    }



    /**
     * 物品数量变更日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ITEM,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.QUANTITY,order = Log.ORDER.BEFORE)
    public static void itemQuantityLogB(JoinPoint joinPoint,Log log){
        commonObjLogB(Item.class, joinPoint, log, item -> item.getName(), item -> String.valueOf(item.getQuantity()));
    }


    @LogHandler(type = Log.TYPE.ITEM,action = {Log.ACTION.ADD,Log.ACTION.SUB},property = Log.PROPERTY.QUANTITY,order = Log.ORDER.AFTER)
    public static void itemQuantityLogA(JoinPoint joinPoint,Log log){
        commonObjLogA(Item.class, joinPoint, log, item -> {
            android.util.Log.e("qianzise","物品数量"+item.getQuantity());
            return String.valueOf(item.getQuantity());
        });
    }




//    ------------------------------物品相关-------------------------------------

//    ------------------------------成就相关-------------------------------------

    /**
     * 删除成就成就
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void achievementDeleteLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Achievement.class, joinPoint, log, achievement -> achievement.getName(), achievement -> JSONObject.toJSONString(achievement));
    }


    /**
     * 新增成就日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void achievementCreateLogA(JoinPoint joinPoint,Log log){
        commonFuncLogB(Achievement.class, joinPoint, log, achievement -> achievement.getName(), achievement -> JSONObject.toJSONString(achievement));
    }



    /**
     * 编辑成就日志(编辑前
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ACHIEVEMENT,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void achievementEditLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Achievement.class, joinPoint, log, achievement -> achievement.getName(), achievement -> JSONObject.toJSONString(achievement));
    }



    /**
     * 获得或者失去成就日志
     * @param joinPoint
     * @param log
     */
    @LogHandler(type = Log.TYPE.ACHIEVEMENT,action = {Log.ACTION.GET,Log.ACTION.LOSE},property = Log.PROPERTY.DEFAULT,order = Log.ORDER.BEFORE)
    public static void achievementLogB(JoinPoint joinPoint,Log log){
        commonFuncLogB(Achievement.class, joinPoint, log, achievement -> achievement.getName(), achievement1 -> String.valueOf(achievement1.isGot()));
    }


    @LogHandler(type = Log.TYPE.ACHIEVEMENT,action = {Log.ACTION.GET,Log.ACTION.LOSE},property = Log.PROPERTY.DEFAULT,order = Log.ORDER.AFTER)
    public static void achievementLogA(JoinPoint joinPoint,Log log){
        commonFuncLogA(Achievement.class, joinPoint, log, achievement -> String.valueOf(achievement.isGot()));
    }





//    ------------------------------成就相关-------------------------------------

}
