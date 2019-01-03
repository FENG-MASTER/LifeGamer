package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 更新能力命令
 */

public class UpdateSkillCommand extends AbsNoCancelableCommand{
    private Skill skill;

    public UpdateSkillCommand(Skill skill) {
        this.skill = skill;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.skill_update)+skill.getName()+App.getContext().getString(R.string.success);
    }
}
