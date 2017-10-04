package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 */

public interface ISkillManager {

    void addSkillPoint(String skillName,int point);

    void addSkill(String name);

    Skill getSkill(String name);

    void removeSkill(String name);

    List<String> getAllSkillName();

    List<Skill> getAllSkill();
}
