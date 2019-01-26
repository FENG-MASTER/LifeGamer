package com.lifegamer.fengmaster.lifegamer.util;

import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTimesTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition;

import java.util.HashMap;
import java.util.Map;

public class TriggerUtil {

    private static final Map<String,String> conditions=new HashMap<>();

    static {
        conditions.put(TaskFailTriggerCondition.class.getName(),"每次失败");
        conditions.put(TaskFinishTimesTriggerCondition.class.getName(),"累计完成");
        conditions.put(TaskFinishTriggerCondition.class.getName(),"每次完成");
    }


    public static String getTriggerDesc(String condition){
        if (conditions.containsKey(condition)){
            return conditions.get(condition);
        }else {
            return "";
        }

    }
}
