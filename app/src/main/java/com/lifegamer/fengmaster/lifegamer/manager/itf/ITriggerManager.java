package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;

import java.util.List;

/**
 * Created by FengMaster on 19/01/08.
 */
public interface ITriggerManager {


    /**
     * 根据ID获取触发器
     * @param id
     * @return
     */
    Trigger getTrigger(long id);


    /**
     * 新增触发器
     * @param triggerInfo
     * @return
     */
    Trigger addTrigger(TriggerInfo triggerInfo);

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


    /**
     * 获取指定种类的指定对象ID上的所有触发器
     * @param type
     * @param objId
     * @return
     */
    List<Trigger> getTriggers(String type, String objId);

}
