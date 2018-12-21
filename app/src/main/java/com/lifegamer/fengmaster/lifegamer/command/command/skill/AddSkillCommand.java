package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.ICommand;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 新增能力命令
 */

public class AddSkillCommand extends AbsCancelableCommand {

    private Skill newSkill;

    public AddSkillCommand(Skill newSkill) {
        this.newSkill = newSkill;
    }

    @Override
    public void execute() {
        if (Game.getInstance().getSkillManager().addSkill(newSkill)){
            EventBus.getDefault().post(new NewSkillEvent(newSkill));

        }
    }

    @Override
    public void undo() {
        Game.getInstance().getSkillManager().removeSkill(newSkill.getName());
    }


    @Override
    public String getName() {
        return App.getContext().getString(R.string.skill_add)+newSkill.getName();
    }


}
