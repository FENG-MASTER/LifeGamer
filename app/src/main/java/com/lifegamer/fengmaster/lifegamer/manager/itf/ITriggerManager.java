package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;

/**
 * Created by FengMaster on 19/01/08.
 */
public interface ITriggerManager {


    /**
     * 根据ID获取触发器列表
     * @param id
     * @return
     */
    TriggerInfo getTrigger(long id);


    /**
     * 新增触发器
     * @param triggerInfo
     * @return
     */
    boolean addTrigger(TriggerInfo triggerInfo);

    /**
     * 更新触发器
     * @param triggerInfo
     * @return
             */
    boolean updateTrigger(TriggerInfo triggerInfo);


    /**
     * 删除触发器
     * @param triggerInfo
     * @return
     */
    boolean removeTrigger(TriggerInfo triggerInfo);

    /**
     * 删除触发器
     * @param triggerId
     * @return
     */
    boolean removeTrigger(long triggerId);

}
