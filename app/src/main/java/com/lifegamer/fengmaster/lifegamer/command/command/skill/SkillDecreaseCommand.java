package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * 能力经验减少命令
 * Created by FengMaster on 18/07/31.
 */
public class SkillDecreaseCommand extends AbsCancelableCommand {
    private Skill skill;
    private long skillID;
    private int decxp;

    public SkillDecreaseCommand(long skillID, int decxp) {
        this.skillID = skillID;
        this.decxp = decxp;
        skill= Game.getInstance().getSkillManager().getSkill(skillID);
    }

    @Override
    public boolean execute() {
        skill.reduceXP(decxp);
        return Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public void undo() {
        skill.addXP(decxp);
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public String getName() {
        return skill.getName()+App.getContext().getString(R.string.sub) + decxp;
    }


    /**
     * 不显示给用户
     * @return
     */
    @Override
    public boolean isShow() {
        return false;
    }
}
