package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.event.task.FailTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.FinishTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 任务结束触发
 * Created by FengMaster on 19/01/09.
 */
public class TaskFinishOrFailTriggerCondition extends AbsTriggerCondition {
    public TaskFinishOrFailTriggerCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        super(triggerInfo, params, onTrigger);
    }

    @Override
    public String getConditionDesc() {
        return "结束任务";
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void taskFinsh(FinishTaskEvent finishTaskEvent){
        Task task = finishTaskEvent.getTask();
        if (task.getId()== triggerInfo.getMainObjId()){
            //如果是触发器指定的任务ID
            trigger();
        }
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
