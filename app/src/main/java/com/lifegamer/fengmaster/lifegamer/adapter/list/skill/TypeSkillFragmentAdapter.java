package com.lifegamer.fengmaster.lifegamer.adapter.list.skill;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.util.List;

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
    public void updateList() {
        showSkillList=Game.getInstance().getSkillManager().getAllSkill(type);
    }
}
