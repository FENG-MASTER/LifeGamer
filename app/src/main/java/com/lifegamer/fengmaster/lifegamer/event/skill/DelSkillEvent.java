package com.lifegamer.fengmaster.lifegamer.event.skill;

/**
 * Created by qianzise on 2017/10/14.
 *
 * 删除能力事件
 */

public class DelSkillEvent {

    private long skillID;
    private String skillName;

    public DelSkillEvent(String skillName,long skillID) {
        this.skillName = skillName;
        this.skillID=skillID;
    }

    public String getSkillName() {
        return skillName;
    }

    public long getSkillID() {
        return skillID;
    }
}
