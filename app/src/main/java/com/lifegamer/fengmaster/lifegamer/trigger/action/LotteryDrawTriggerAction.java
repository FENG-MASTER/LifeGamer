package com.lifegamer.fengmaster.lifegamer.trigger.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽奖池任务触发动作
 * Created by FengMaster on 19/03/15.
 */
public class LotteryDrawTriggerAction extends AbsTriggerAction {

    private Task origTask;

    /**
     * 是否已经变成一般类型任务
     */
    private boolean turn;

    public LotteryDrawTriggerAction(String params,TriggerInfo triggerInfo) {
        super(params,triggerInfo);
        origTask = JSONObject.parseObject(params, Task.class);
    }

    @Override
    void trigger() {
        //触发条件了


    }


}
