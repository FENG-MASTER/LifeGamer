package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/10.
 * <p>
 * 显示所有能力的适配器
 */

public class AllSkillFragmentAdapter extends BaseSkillFragmentAdapter {


    @Override
    public String getName() {
        return App.getContext().getString(R.string.skill_all);
    }


    @Override
    public void updateShowList() {
        showList=Game.getInstance().getSkillManager().getAllSkill();
    }


    @Override
    public boolean hasData() {
        return true;
    }
}
