package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.task.FailTaskCommand;
import com.lifegamer.fengmaster.lifegamer.event.trigger.MinuteEvent;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.util.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.TimerTask;

public class TaskExpireCondition extends AbsTriggerCondition {


    private Task task;

    private boolean enable = true;


    @Override
    public void invalid() {
        enable = false;
    }

    @Override
    public void valid() {
        enable = true;
    }

    public TaskExpireCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        super(triggerInfo, params, onTrigger);
    }

    @Override
    public String getConditionDesc() {
        return "任务自动失败";
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void run(MinuteEvent minuteEvent) {
        if (!enable) {
            return;
        }
        task = Game.getInstance().getTaskManager().getTask(triggerInfo.getMainObjId());
        Date lastExpireDate = null;
        if (triggerInfo.getSaveInfo() != null && !triggerInfo.getSaveInfo().isEmpty()) {
            lastExpireDate = new Date(Long.valueOf(triggerInfo.getSaveInfo()));
        }

        //不限次数任务无法自动失败,没有任何意义
        if (task != null&&task.isAutoFail()&&task.getRepeatType()!=Task.REP_CONTINUOUS) {

            if (lastExpireDate != null) {
                //没有历史触发时间,
                if (lastExpireDate.after(task.getExpirationTime())) {
                    return;
                }
            }

            while (minuteEvent.getDate().after(task.getExpirationTime())) {
                triggerInfo.setSaveInfo(String.valueOf(minuteEvent.getDate().getTime()));
                Game.getInstance().getCommandManager().executeCommand(new FailTaskCommand(task));
                Game.getInstance().getTriggerManager().updateTriggerInfo(triggerInfo);
            }

        }
    }




}
