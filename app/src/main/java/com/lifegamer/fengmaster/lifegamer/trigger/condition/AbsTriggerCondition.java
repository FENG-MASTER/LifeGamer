package com.lifegamer.fengmaster.lifegamer.trigger.condition;

import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 触发器 触发条件基类
 * 禁止直接生成本对象实例,必须继承
 * Created by FengMaster on 19/01/08.
 */
public abstract class AbsTriggerCondition {

    /**
     * 触发条件参数
     */
    protected String params;

    /**
     * 触发器信息实体
     */
    protected TriggerInfo triggerInfo;

    /**
     * 条件达成后,会回调这个函数通知触发器
     */
    protected OnTrigger onTriggerListener;

    public AbsTriggerCondition(TriggerInfo triggerInfo, String params, OnTrigger onTrigger) {
        this.params = params;
        this.triggerInfo = triggerInfo;
        this.onTriggerListener=onTrigger;
        EventBus.getDefault().register(this);
    }

    /**
     * 触发器失效,调用后,本触发器将不再被触发
     */
    public void invalid(){
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 触发器生效
     */
    public void valid(){
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }



    /**
     * 触发回调接口
     */
    public interface OnTrigger{
        void trigger(AbsTriggerCondition condition);
    }

    protected void trigger(){
        if (onTriggerListener!=null){
            onTriggerListener.trigger(this);
        }
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public abstract String getConditionDesc();

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void enpty(Object o){

    }
}
