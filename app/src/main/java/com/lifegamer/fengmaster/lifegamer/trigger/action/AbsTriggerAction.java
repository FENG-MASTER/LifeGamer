package com.lifegamer.fengmaster.lifegamer.trigger.action;

import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

/**
 * 触发器附加动作基类
 * Created by FengMaster on 19/03/14.
 */
public abstract class AbsTriggerAction {

    public AbsTriggerAction( TriggerInfo triggerInfo,String params) {
        this.params = params;
        this.triggerInfo=triggerInfo;
    }

    /**
     * 触发器动作参数
     */
    protected String params;

    protected TriggerInfo triggerInfo;

    /**
     * 满足触发条件后,正式触发触发器动作
     */
   public abstract boolean trigger();

}
