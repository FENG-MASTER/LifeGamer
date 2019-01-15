package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.event.task.FailTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 任务失败触发条件
 * Created by FengMaster on 19/01/09.
 */
public class TaskFailTriggerCondition extends AbsTriggerCondition {

    public TaskFailTriggerCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        super(triggerInfo, params, onTrigger);
    }

    @Override
    public String getConditionDesc() {
        return "失败一次触发";
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void taskFail(FailTaskEvent failTaskEvent){
        Task task = failTaskEvent.getTask();
        if (task.getId()== triggerInfo.getMainObjId()){
            //如果是触发器指定的任务ID
            trigger();
        }
    }
}
