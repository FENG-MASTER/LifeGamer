package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.event.skill.DelSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/21.
 *
 * 删除技能命令
 */

public class RemoveSkillCommand extends AbsCancelableCommand {
    private Skill skill;

    public RemoveSkillCommand(Skill skill) {
        this.skill = skill;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getSkillManager().removeSkill(skill.getName())){
            EventBus.getDefault().post(new DelSkillEvent(skill.getName()));
        }
    }

    @Override
    public void undo() {
        Game.getInstance().getSkillManager().addSkill(skill);
    }

    @Override
    public String getName() {
        return "删除技能"+skill.getName()+"成功";
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
