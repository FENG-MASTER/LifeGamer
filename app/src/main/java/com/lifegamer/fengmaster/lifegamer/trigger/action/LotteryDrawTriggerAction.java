package com.lifegamer.fengmaster.lifegamer.trigger.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import java.util.List;

/**
 * 抽奖池任务触发动作,
 *
 * 当抽中某任务时候,该任务会默认新增本触发动作,当该任务失败或者完成,都会把任务修改为奖池任务
 *
 * Created by FengMaster on 19/03/15.
 */
public class LotteryDrawTriggerAction extends AbsTriggerAction {

    private Task origTask;


    public LotteryDrawTriggerAction(TriggerInfo triggerInfo,String params) {
        super(triggerInfo,params);
        if (triggerInfo.getType().equals(TriggerInfo.TYPE_TASK)){
            //任务触发器
            origTask=Game.getInstance().getTaskManager().getTask(triggerInfo.getMainObjId());
        }
    }

    @Override
    public boolean trigger() {
        //触发条件

        //1. 把任务放回奖池
        origTask.setRepeatType(Task.REP_LOTTERY_DRAW);

        //2. 设置任务时间为无限期
        origTask.setExpirationTime(Task.noDate);

        //3. 去掉前面配备的触发器  TaskFinishOrFailTriggerCondition

        List<TriggerInfo> triggerInfos = Stream.of(origTask.getTriggerInfos()).collect(Collectors.toList());
        triggerInfos.remove(triggerInfo);
        origTask.setTriggerInfos(triggerInfos);

        Game.getInstance().getTaskManager().updateTask(origTask);
        return true;
    }


}
