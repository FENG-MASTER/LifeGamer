package com.lifegamer.fengmaster.lifegamer.log.undo;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by FengMaster on 18/12/13.
 */
public class UndoHandlers {

//--------------------------------------技能相关-------------------------------------------//

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.ADD, property = Log.PROPERTY.XP)
    public static void skillXpAddUndo(Log log) {
        String operName = log.getOperName();
        Skill skill = Game.getInstance().getSkillManager().getSkill(operName);
        skill.setXP(skill.getXP() - Integer.valueOf(log.getValue()));
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @UndoHandler(type = Log.TYPE.SKILL, action = Log.ACTION.SUB, property = Log.PROPERTY.XP)
    public static void skillXpSubUndo(Log log) {
        String operName = log.getOperName();
        Skill skill = Game.getInstance().getSkillManager().getSkill(operName);
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

//--------------------------------------技能相关-------------------------------------------//


//--------------------------------------英雄相关-------------------------------------------//


    @UndoHandler(type = Log.TYPE.HERO, action = Log.ACTION.ADD, property = Log.PROPERTY.LIFEPOINT)
    public static void heroLifepointAddUndo(Log log) {
        LifePoint lifePoint = Game.getInstance().getHeroManager().getHero().getLifePoint();
        lifePoint.setLpPoint(lifePoint.getLpPoint() - Integer.valueOf(log.getValue()));

    }



//--------------------------------------英雄相关-------------------------------------------//




}
