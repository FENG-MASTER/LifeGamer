package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/21.
 *
 * 删除能力命令
 */

public class RemoveSkillCommand extends AbsCancelableCommand {
    private Skill skill;

    public RemoveSkillCommand(Skill skill) {
        this.skill = skill;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getSkillManager().removeSkill(skill.getName())){
            EventBus.getDefault().post(new DelSkillEvent(skill.getName(),skill.getId()));
        }
    }

    @Override
    public void undo() {
        Game.getInstance().getSkillManager().addSkill(skill);
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.skill_del)+skill.getName()+App.getContext().getString(R.string.success);
    }


}
