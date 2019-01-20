package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.BaseSkillFragmentAdapter;

/**
 * 按类型显示成就Adapter
 * Created by qianzise on 2017/10/10.
 */
public class TypeAchievementFragmentAdapter extends BaseAchievementFragmentAdapter{

    private String type;

    public TypeAchievementFragmentAdapter(String type) {
        super();
        this.type = type;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public void updateShowList() {
        showList=Game.getInstance().getAchievementManager().getAllAchievement(type);

    }
}
