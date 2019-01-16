package com.lifegamer.fengmaster.lifegamer.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
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



    public static void try2InitDBData(){
        SharedPreferences preferences = App.getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        if (preferences.getBoolean(FIRST_OPEN,true)){
            //第一次执行,需要初始化数据
            initSkillsData(App.getContext());

            preferences.edit().putBoolean(FIRST_OPEN,false).commit();
        }
    }


    /**
     * 初始化技能相关
     */
    private static void initSkillsData(Context context){
        ISkillManager skillManager = Game.getInstance().getSkillManager();

        String[][] skills=new String[][]{
                {context.getString(R.string.db_default_fitness),context.getString(R.string.db_default_physical_fitness),context.getString(R.string.db_default_fitness_desc),"gmd#gmd_healing"},
                {context.getString(R.string.db_default_leg_strength),context.getString(R.string.db_default_physical_fitness),context.getString(R.string.db_default_leg_strength_desc),"cmd#cmd_brain"},
                {context.getString(R.string.db_default_arm_strength),context.getString(R.string.db_default_physical_fitness),context.getString(R.string.db_default_arm_strength_desc),"gmd#gmd_insert_emoticon"},
                {context.getString(R.string.db_default_waist_strength),context.getString(R.string.db_default_physical_fitness),context.getString(R.string.db_default_waist_desc),"ion#ion_ios_body"},
                {context.getString(R.string.db_default_iq),context.getString(R.string.db_default_brain),context.getString(R.string.db_default_iq_desc),"cmd#cmd_brain"},
                {context.getString(R.string.db_default_eq),context.getString(R.string.db_default_brain),context.getString(R.string.db_default_eq_desc),"ion#ion_ios_people"},
                {context.getString(R.string.db_default_happiness),context.getString(R.string.db_default_emotion),context.getString(R.string.db_default_happiness_desc),"cmd#cmd_emoticon_happy"},
                {context.getString(R.string.db_default_anxiety),context.getString(R.string.db_default_emotion),context.getString(R.string.db_default_anxiety_desc),"cmd#cmd_emoticon_neutral"},
                {context.getString(R.string.db_default_happy),context.getString(R.string.db_default_emotion),context.getString(R.string.db_default_happy_desc),"ion#ion_android_happy"},
                {context.getString(R.string.db_default_sad),context.getString(R.string.db_default_emotion),context.getString(R.string.db_default_sad_desc),"ent#ent_emoji_sad"},
                {context.getString(R.string.db_default_code_skill),context.getString(R.string.db_default_skill),context.getString(R.string.db_default_code_skill_desc),"faw#faw_code"},
                {context.getString(R.string.db_default_communication_skill),context.getString(R.string.db_default_skill),context.getString(R.string.db_default_communication_skill_desc),"ion#ion_android_contacts"},
                {context.getString(R.string.db_default_understanding_skill),context.getString(R.string.db_default_skill),context.getString(R.string.db_default_understanding_skill_desc),"gmi#gmi_quote"},
                {context.getString(R.string.db_default_think_skill),context.getString(R.string.db_default_skill),context.getString(R.string.db_default_think_skill_desc),"oct#oct_question"},
                {context.getString(R.string.db_default_cooking_skill),context.getString(R.string.db_default_skill),context.getString(R.string.db_default_cooking_skill_desc),"cmd#cmd_food_variant"},
                {"颜值",context.getString(R.string.db_default_physical_fitness),"颜值即正义","cmd#cmd_face"}

        };

        for (String[] skillStr : skills) {
            Skill skill = new Skill();
            skill.setName(skillStr[0]);
            skill.setType(skillStr[1]);
            skill.setIntro(skillStr[2]);
            skill.setIcon(skillStr[3]);
            skillManager.addSkill(skill);
        }


    }


}
