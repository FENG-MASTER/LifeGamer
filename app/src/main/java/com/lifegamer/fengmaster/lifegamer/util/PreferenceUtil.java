package com.lifegamer.fengmaster.lifegamer.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.lifegamer.fengmaster.lifegamer.App;

import java.util.HashSet;
import java.util.Set;

public class PreferenceUtil {


    public static final String PREFERENCE_NAME="setting";

    private static final String show_fragment_task_type="show_fragment_task_type";
    private static final String show_fragment_reward_type="show_fragment_reward_type";
    private static final String show_fragment_item_type="show_fragment_item_type";
    private static final String show_fragment_skill_type="show_fragment_skill_type";
    private static final String show_fragment_achievement_type="show_fragment_achievement_type";


    private static SharedPreferences sharedPreferences=App.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);


    /**
     * 检查fragmeng是否设置可以显示
     * @param fragmentName fragment名称
     * @return
     */
    public static final boolean checkIfShow(String fragmentName){
        if (fragmentName.toLowerCase().contains("task")){
            Set<String> taskType = sharedPreferences.getStringSet(show_fragment_task_type, null);
            if (taskType==null){
                return true;
            }
            return taskType.contains(fragmentName);
        }

        if (fragmentName.toLowerCase().contains("skill")){
            Set<String> taskType = sharedPreferences.getStringSet(show_fragment_skill_type, null);
            if (taskType==null){
                return true;
            }
            return taskType.contains(fragmentName);
        }

        if (fragmentName.toLowerCase().contains("achievement")){
            Set<String> taskType = sharedPreferences.getStringSet(show_fragment_achievement_type, null);
            if (taskType==null){
                return true;
            }
            return taskType.contains(fragmentName);
        }

        if (fragmentName.toLowerCase().contains("item")){
            Set<String> taskType = sharedPreferences.getStringSet(show_fragment_item_type, null);
            if (taskType==null){
                return true;
            }
            return taskType.contains(fragmentName);
        }

        if (fragmentName.toLowerCase().contains("reward")){
            Set<String> taskType = sharedPreferences.getStringSet(show_fragment_reward_type, null);
            if (taskType==null){
                return true;
            }
            return taskType.contains(fragmentName);
        }


        return true;

    }


}
