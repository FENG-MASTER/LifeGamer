package com.lifegamer.fengmaster.lifegamer.command.command.skill;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

/**
 * 技能经验增加命令
 * Created by FengMaster on 18/07/31.
 */
public class SkillIncreaseCommand extends AbsCancelableCommand {
    private Skill skill;
    private long skillID;
    private int incxp;

    public SkillIncreaseCommand(long skillID, int incxp) {
        this.skillID = skillID;
        this.incxp = incxp;
        skill= Game.getInstance().getSkillManager().getSkill(skillID);
    }

    @Override
    public void execute() {
        skill.addXP(incxp);
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public void undo() {
        skill.addXP(-incxp);
        Game.getInstance().getSkillManager().updateSkill(skill);
    }

    @Override
    public String getName() {
        return skill.getName()+"增加了" +incxp;
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
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
