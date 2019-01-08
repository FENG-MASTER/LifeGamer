package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.event.task.FinishTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 每次任务完成触发
 * Created by FengMaster on 19/01/09.
 */
public class TaskFinishTriggerCondition extends AbsTriggerCondition {
    public TaskFinishTriggerCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        super(triggerInfo, params, onTrigger);
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void taskFinsh(FinishTaskEvent finishTaskEvent){
        Task task = finishTaskEvent.getTask();
        if (task.getId()== triggerInfo.getMainObjId()){
            //如果是触发器指定的任务ID
            trigger();
        }
    }

}
