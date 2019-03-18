package com.lifegamer.fengmaster.lifegamer.trigger.action;

/**
 * 触发器动作基类
 * Created by FengMaster on 19/03/14.
 */
public abstract class AbsTriggerAction {

    public AbsTriggerAction(String params) {
        this.params = params;
    }

    /**
     * 触发器动作参数
     */
    protected String params;

    /**
     * 满足触发条件后,正式触发触发器动作
     */
   abstract void trigger();

}
