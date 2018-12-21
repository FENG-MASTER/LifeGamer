package com.lifegamer.fengmaster.lifegamer.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by FengMaster on 18/12/21.
 *
 * 默认第一次打开软件的初始化数据库数据
 */
public class DefaultDBData {

    private static final String PREFERENCES_NAME="db";

    private static final String FIRST_OPEN="first_open";

    public void try2InitDBData(){
        SharedPreferences preferences = App.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferences.getBoolean(FIRST_OPEN,true)){
            //第一次执行,需要初始化数据
            initSkillsData();
        }
    }


    /**
     * 初始化技能相关
     */
    private void initSkillsData(){
        ISkillManager skillManager = Game.getInstance().getSkillManager();

        String[][] skills=new String[][]{
                {"体能","身体素质","一般通过锻炼可以增强","gmd#gmd_healing"},
                {"腿部力量","身体素质","腿部锻炼可以增强","cmd#cmd_brain"},
                {"手臂力量","身体素质","手臂锻炼可以增强","gmd#gmd_insert_emoticon"},
                {"腰部力量","身体素质","腰部锻炼可以增强"},
                {"智商","大脑","有的人天生就聪明"},
                {"情商","大脑","处理人情世故的能力"},
                {"幸福感","情绪","生活幸福的程度"},
                {"焦虑感","情绪","焦虑"},
                {"开心","情绪","开心"},
                {"难过","情绪","难过"},
                {"开发能力","技能","编程能力"},
                {"沟通能力","技能","与人沟通的能力"},
                {"理解能力","技能","理解事物的能力"},
                {"思考能力","技能","思考事情的能力"},
                {"厨艺","技能","生活中必不可少的技能"}
        };

        for (String[] skillStr : skills) {
            Skill skill = new Skill();
            skill.setName(skillStr[0]);
            skill.setType(skillStr[1]);
            skill.setIntro(skillStr[2]);
        }


    }


}
