package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/10.
 * <p>
 * 显示所有技能的适配器
 */

public class AllSkillFragmentAdapter extends BaseSkillFragmentAdapter {


    @Override
    public String getName() {
        return "全部";
    }

    @Override
    public void updateList() {
        showSkillList=Game.getInstance().getSkillManager().getAllSkill();
    }
}
