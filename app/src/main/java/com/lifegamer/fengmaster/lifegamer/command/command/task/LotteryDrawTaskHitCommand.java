package com.lifegamer.fengmaster.lifegamer.command.command.task;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.task.LotteryDrawTaskTurnEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.action.LotteryDrawTriggerAction;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishOrFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.util.TriggerUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 抽奖抽中相应任务
 * Created by FengMaster on 19/10/23.
 */
public class LotteryDrawTaskHitCommand extends AbsNoCancelableCommand {

    private Task task;
    private Date deadLineDate;

    public LotteryDrawTaskHitCommand(Task task, Date deadLineDate) {
        this.task = task;
        this.deadLineDate=deadLineDate;
    }

    @Override
    public boolean execute() {
        //如果是抽奖任务
        if (task.getRepeatType()==Task.REP_LOTTERY_DRAW){

            List<TriggerInfo> triggerInfos = Stream.of(task.getTriggerInfos()).collect(Collectors.toList());

            //1. 设置抽中的任务为一次性任务
            task.setRepeatType(Task.REP_ONCE);


            //2.设置任务截止日


            task.setExpirationTime(deadLineDate);

            //3. 设置任务完成(失败)奖励为 修改当前任务为抽奖任务
            TriggerInfo triggerInfo1 = generateBaseTriggerInfo();
            triggerInfo1.setTriggerCondition(TaskFinishOrFailTriggerCondition.class.getName());
            triggerInfo1.setTriggerConditionDesc(TriggerUtil.getTriggerDesc(TaskFinishOrFailTriggerCondition.class.getName()));
            triggerInfos.add(triggerInfo1);

            task.setTriggerInfos(triggerInfos);

            Game.getInstance().getTaskManager().updateTask(task);

            return true;
        }else {
            return false;
        }

    }


    private TriggerInfo generateBaseTriggerInfo(){
        TriggerInfo triggerInfo=new TriggerInfo();
        triggerInfo.setMainObjId(task.getId());
        triggerInfo.setType(TriggerInfo.TYPE_TASK);
        triggerInfo.setTriggerAction(LotteryDrawTriggerAction.class.getName());
        triggerInfo.setTriggerActionDesc(TriggerUtil.getTriggerDesc(triggerInfo.getTriggerAction()));
        return triggerInfo;
    }

    @Override
    public String getName() {
        return "新增任务"+task.getName();
    }

    @Override
    public boolean isShow() {
        return false;
    }
}
