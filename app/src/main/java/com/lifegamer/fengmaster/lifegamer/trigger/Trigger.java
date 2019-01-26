package com.lifegamer.fengmaster.lifegamer.trigger;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.GotAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.LoseAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.SkillDecreaseCommand;
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

        if (condition!=null){
            triggerInfo.setTriggerConditionDesc(condition.getConditionDesc());
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
     * 触发器失效,调用后本触发器将不再触发
     */
    public void invalid(){
        condition.invalid();
    }


    /**
     * 触发器生效
     */
    public void valid(){
        condition.valid();
    }


    /**
     * 处理lp点数
     *
     * @param triggerInfo
     */
    private void handleLifePoint(TriggerInfo triggerInfo) {
        int lp = triggerInfo.getEarnLP();
        if (lp==0){
            return;
        }
        if(lp>0){
            Game.getInstance().getHeroManager().getHero().getLifePoint().addPoint(lp);
        }else {
            Game.getInstance().getHeroManager().getHero().getLifePoint().subPoint(-lp);
        }
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

                    if (achievement.getProbability()>=0){
                        //获得成就

                        if (am != null && !am.isGot()) {
                            Game.getInstance().getCommandManager().executeCommand(new GotAchievementCommand(am));
                        }

                    }else {
                        //失去成就
                        if (am != null && am.isGot()) {
                            Game.getInstance().getCommandManager().executeCommand(new LoseAchievementCommand(am));
                        }
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
                if (itemReward.getNum()>=0){
                    //获得相应物品
                    Game.getInstance().getRewardManager().gainRewardItem((int) itemReward.getRewardID(), itemReward.getNum());
                }else {
                    //失去物品
                    Game.getInstance().getRewardManager().lostRewardItem((int) itemReward.getRewardID(), -itemReward.getNum());
                }

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
            if (entry.getValue()>=0){
                Game.getInstance().getCommandManager().executeCommand(new SkillIncreaseCommand(entry.getKey(), entry.getValue()));
            }else {
                Game.getInstance().getCommandManager().executeCommand(new SkillDecreaseCommand(entry.getKey(), -entry.getValue()));

            }
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

            if (triggerInfo.getXp()>=0){
                hero.addXp(triggerInfo.getXp());
            }else {
                hero.reduceXp(-triggerInfo.getXp());
            }

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
