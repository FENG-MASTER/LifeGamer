package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 更新技能命令
 */

public class UpdateSkillCommand extends AbsNoCancelableCommand{
    private Skill skill;

    public UpdateSkillCommand(Skill skill) {
        this.skill = skill;
    }

    @Override
    public void execute() {
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public String getName() {
        return "更新技能 "+skill.getName()+" 成功";
    }
}
