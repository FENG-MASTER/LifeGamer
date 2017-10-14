package com.lifegamer.fengmaster.lifegamer.event.skill;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 删除技能事件
 */

public class DelSkillEvent {

    private String skillName;

    public DelSkillEvent(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillName() {
        return skillName;
    }
}
