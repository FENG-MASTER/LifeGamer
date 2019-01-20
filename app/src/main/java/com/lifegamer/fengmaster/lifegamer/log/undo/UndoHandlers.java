package com.lifegamer.fengmaster.lifegamer.log.undo;

import com.alibaba.fastjson.JSONObject;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.AddSkillCommand;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by FengMaster on 18/12/13.
 */
public class UndoHandlers {

//--------------------------------------能力相关-------------------------------------------//

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.ADD, property = Log.PROPERTY.XP)
    public static void skillXpAddUndo(Log log) {
        Skill skill = Game.getInstance().getSkillManager().getSkill(Long.valueOf(log.getOperId()));
        skill.setXP(skill.getXP() - Integer.valueOf(log.getValue()));
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.SUB, property = Log.PROPERTY.XP)
    public static void skillXpSubUndo(Log log) {
        Skill skill = Game.getInstance().getSkillManager().getSkill(Long.valueOf(log.getOperId()));
        skill.setXP(skill.getXP() + Integer.valueOf(log.getValue()));
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.SUB, property = Log.PROPERTY.LEVEL)
    public static void skillLevelSubUndo(Log log) {
        //xp处理了,不需要处理level
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.ADD, property = Log.PROPERTY.LEVEL)
    public static void skillLevelAddUndo(Log log) {
        //xp处理了,不需要处理level
    }


    /**
     * 回滚技能删除动作
     * @param log
     */
    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.DELETE, property = Log.PROPERTY.DEFAULT)
    public static void skillDeleteUndo(Log log) {
        Skill skill= JSONObject.parseObject(log.getOldValue(),Skill.class);
        Game.getInstance().getSkillManager().addSkill(skill);
    }

    /**
     * 回滚技能新建动作
     * @param log
     */
    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.CREATE, property = Log.PROPERTY.DEFAULT)
    public static void skillCreateUndo(Log log) {
        Skill skill= JSONObject.parseObject(log.getOldValue(),Skill.class);
        Game.getInstance().getSkillManager().removeSkill(skill.getName());
    }



//--------------------------------------能力相关-------------------------------------------//


//--------------------------------------英雄相关-------------------------------------------//


    @UndoHandler(type = Log.TYPE.HERO, action = Log.ACTION.ADD, property = Log.PROPERTY.LIFEPOINT)
    public static void heroLifepointAddUndo(Log log) {
        LifePoint lifePoint = Game.getInstance().getHeroManager().getHero().getLifePoint();
        lifePoint.setLpPoint(lifePoint.getLpPoint() - Integer.valueOf(log.getValue()));

    }


    @UndoHandler(type = Log.TYPE.HERO, action = Log.ACTION.SUB, property = Log.PROPERTY.LIFEPOINT)
    public static void heroLifepointSubUndo(Log log) {
        LifePoint lifePoint = Game.getInstance().getHeroManager().getHero().getLifePoint();
        lifePoint.setLpPoint(lifePoint.getLpPoint() + Integer.valueOf(log.getValue()));

    }

    @UndoHandler(type = Log.TYPE.HERO, action = Log.ACTION.ADD, property = Log.PROPERTY.XP)
    public static void heroXpAddUndo(Log log) {
        int xp = Game.getInstance().getHeroManager().getHero().getXp();
        Game.getInstance().getHeroManager().getHero().setXp(xp-Integer.valueOf(log.getValue()));
    }


    @UndoHandler(type = Log.TYPE.HERO, action = Log.ACTION.SUB, property = Log.PROPERTY.XP)
    public static void heroXpSubUndo(Log log) {
        int xp = Game.getInstance().getHeroManager().getHero().getXp();
        Game.getInstance().getHeroManager().getHero().setXp(xp+Integer.valueOf(log.getValue()));
    }


//--------------------------------------英雄相关-------------------------------------------//



//--------------------------------------物品相关-------------------------------------------//


    @UndoHandler(type = Log.TYPE.ITEM, action = Log.ACTION.ADD, property = Log.PROPERTY.QUANTITY)
    public static void itemQuantityAddUndo(Log log) {
        Item item = Game.getInstance().getItemManager().getItem(Long.valueOf(log.getOperId()));
        if (item.getQuantity()>Integer.valueOf(log.getValue())){
            //物品数量足够,直接减去相应数目即可
            item.setQuantity(item.getQuantity()-Integer.valueOf(log.getValue()));
            Game.getInstance().getItemManager().updateItem(item);
        }else if (item.getQuantity()<Integer.valueOf(log.getValue())){
            //物品数量不够,直接归零
            item.setQuantity(0);
            Game.getInstance().getItemManager().updateItem(item);
        }else {
            item.setQuantity(0);
            Game.getInstance().getItemManager().updateItem(item);
        }

    }


    @UndoHandler(type = Log.TYPE.ITEM, action = Log.ACTION.SUB, property = Log.PROPERTY.QUANTITY)
    public static void itemQuantitySubUndo(Log log) {
        Item item = Game.getInstance().getItemManager().getItem(Long.valueOf(log.getOperId()));
        item.setQuantity(item.getQuantity()+Integer.valueOf(log.getValue()));
        Game.getInstance().getItemManager().updateItem(item);
    }



//--------------------------------------物品相关-------------------------------------------//


//--------------------------------------成就相关-------------------------------------------//



    @UndoHandler(type = Log.TYPE.ACHIEVEMENT, action = Log.ACTION.GET, property = Log.PROPERTY.DEFAULT )
    public static void achievementGetUndo(Log log) {
        Achievement achievement = Game.getInstance().getAchievementManager().getAchievement(Long.valueOf(log.getOperId()));
        if (achievement.isGot()){
            achievement.setGot(false);
            Game.getInstance().getAchievementManager().updateAchievement(achievement);
        }
    }


    @UndoHandler(type = Log.TYPE.ACHIEVEMENT, action = Log.ACTION.LOSE, property = Log.PROPERTY.DEFAULT )
    public static void achievementLostUndo(Log log) {
        Achievement achievement = Game.getInstance().getAchievementManager().getAchievement(Long.valueOf(log.getOperId()));
        if (!achievement.isGot()){
            achievement.setGot(true);
            Game.getInstance().getAchievementManager().updateAchievement(achievement);
        }
    }


//--------------------------------------成就相关-------------------------------------------//

//--------------------------------------任务相关-------------------------------------------//


    /**
     * 任务完成撤销
     * @param log
     */
    @UndoHandler(type = Log.TYPE.TASK, action = Log.ACTION.FINISH, property = Log.PROPERTY.TASK)
    public static void taskFinishUndo(Log log) {
        Game.getInstance().getTaskManager().undoFinishTask(Long.valueOf(log.getOperId()));
    }

    @UndoHandler(type = Log.TYPE.TASK, action = Log.ACTION.FAIL, property = Log.PROPERTY.TASK)
    public static void taskFailUndo(Log log) {
        Game.getInstance().getTaskManager().undoFailTask(Long.valueOf(log.getOperId()));
    }






//--------------------------------------任务相关-------------------------------------------//

}
