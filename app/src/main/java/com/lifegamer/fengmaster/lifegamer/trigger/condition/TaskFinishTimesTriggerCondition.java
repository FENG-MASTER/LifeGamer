package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.event.task.FinishTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 任务完成累积次数触发器条件
 * Created by FengMaster on 19/01/08.
 */
public class TaskFinishTimesTriggerCondition extends AbsTriggerCondition {

    /**
     * 完成任务超过以上次数,则触发
     */
    private int triggerFinishTime=-1;

    public TaskFinishTimesTriggerCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        super(triggerInfo, params,onTrigger);
        triggerFinishTime=Integer.valueOf(params);
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void taskFinsh(FinishTaskEvent finishTaskEvent){
        Task task = finishTaskEvent.getTask();
        if (task.getId()!= triggerInfo.getMainObjId()){
            return;
        }
        //如果是触发器指定的任务ID
        if (task.getCompleteTimes()==triggerFinishTime){
            //任务完成次数等于指定的触发值
            trigger();
        }
    }


}
