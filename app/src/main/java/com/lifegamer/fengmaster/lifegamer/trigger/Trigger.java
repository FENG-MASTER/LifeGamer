package com.lifegamer.fengmaster.lifegamer.trigger;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.GotAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.SkillIncreaseCommand;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.AbsTriggerCondition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 触发器本体,担任了触发的功能
 * Created by FengMaster on 19/01/08.
 */
public class Trigger implements AbsTriggerCondition.OnTrigger {

    private TriggerInfo triggerInfo;

    private AbsTriggerCondition condition;

    public Trigger(TriggerInfo triggerInfo) {
        this.triggerInfo = triggerInfo;
        if (triggerInfo.getTriggerCondition()==null||triggerInfo.getTriggerCondition().isEmpty()){
            return;
        }
        try {
            Constructor<?> constructor = Class.forName(triggerInfo.getTriggerCondition()).
                    getConstructor(TriggerInfo.class, String.class, AbsTriggerCondition.OnTrigger.class);
            condition = (AbsTriggerCondition) constructor.newInstance(triggerInfo, triggerInfo.getTriggerParameter(), this);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void trigger(AbsTriggerCondition condition) {
        //触发条件了
        //获取相应的奖励
        handleLifePoint(triggerInfo);
        handleAchievement(triggerInfo);
        handleItemReward(triggerInfo);
        handleSkillReward(triggerInfo);
        handleXP(triggerInfo);
    }


    /**
     * 处理lp点数
     *
     * @param triggerInfo
     */
    private void handleLifePoint(TriggerInfo triggerInfo) {
        int lp = triggerInfo.getEarnLP();
        Game.getInstance().getHeroManager().getHero().getLifePoint().addPoint(lp);
    }


    /**
     * 处理成就相关奖励
     *
     * @param triggerInfo
     */
    private void handleAchievement(TriggerInfo triggerInfo) {
        List<AchievementReward> achievements = triggerInfo.getAchievements();
        if (achievements != null && !achievements.isEmpty()) {
            //有奖励成就
            for (AchievementReward achievement : achievements) {
                if (achievement.isHit()) {
                    Achievement am = Game.getInstance().getAchievementManager().getAchievement(achievement.getAchievementID());
                    if (am != null && !am.isGot()) {
                        Game.getInstance().getCommandManager().executeCommand(new GotAchievementCommand(am));
                    }
                }
            }

        }

    }

    /**
     * 处理物品奖励
     *
     * @param triggerInfo
     */
    private void handleItemReward(TriggerInfo triggerInfo) {

        List<RandomItemReward> itemRewards = triggerInfo.getItems();
        for (RandomItemReward itemReward : itemRewards) {
            if (itemReward.isHit()) {
                //获得相应物品
                Game.getInstance().getRewardManager().gainRewardItem((int) itemReward.getRewardID(), itemReward.getNum());

            }
        }

    }

    /**
     * 处理能力奖励
     *
     * @param triggerInfo
     */
    private void handleSkillReward(TriggerInfo triggerInfo) {

        Map<Long, Integer> map = triggerInfo.getSkills();
        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            Game.getInstance().getCommandManager().executeCommand(new SkillIncreaseCommand(entry.getKey(), entry.getValue()));
        }

    }

    /**
     * 处理任务经验
     *
     * @param triggerInfo
     */
    private void handleXP(TriggerInfo triggerInfo) {
        if (triggerInfo.getXp() != 0) {
            Hero hero = Game.getInstance().getHeroManager().getHero();
            hero.addXp(triggerInfo.getXp());
            Game.getInstance().getHeroManager().updateHero(hero);
        }

    }

    public TriggerInfo getTriggerInfo() {
        return triggerInfo;
    }

    public AbsTriggerCondition getCondition() {
        return condition;
    }
}
