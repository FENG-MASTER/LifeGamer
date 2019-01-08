package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Trigger;

/**
 * Created by FengMaster on 19/01/08.
 */
public interface ITriggerManager {


    /**
     * 根据ID获取触发器列表
     * @param id
     * @return
     */
    Trigger getTrigger(long id);


    /**
     * 新增触发器
     * @param trigger
     * @return
     */
    boolean addTrigger(Trigger trigger);

    /**
     * 更新触发器
     * @param trigger
     * @return
             */
    boolean updateTrigger(Trigger trigger);


    /**
     * 删除触发器
     * @param trigger
     * @return
     */
    boolean removeTrigger(Trigger trigger);

    /**
     * 删除触发器
     * @param triggerId
     * @return
     */
    boolean removeTrigger(long triggerId);

}
