package com.lifegamer.fengmaster.lifegamer.event.skill;

import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by qianzise on 2017/10/11.
 */

public class NewSkillEvent {

    private Skill newSkill;

    public NewSkillEvent(Skill newSkill) {
        this.newSkill = newSkill;
    }

    public Skill getNewSkill() {
        return newSkill;
    }
}
