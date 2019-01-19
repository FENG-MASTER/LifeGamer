package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/10.
 */

public class TypeSkillFragmentAdapter extends BaseSkillFragmentAdapter{

    private String type;

    public TypeSkillFragmentAdapter(String type) {
        super();
        this.type = type;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public void updateShowList() {
        showList=Game.getInstance().getSkillManager().getAllSkill(type);

    }
}
