package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;

import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.AllSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.TypeSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;

/**
 * 技能fragment
 *
 * 属于高层模块,负责整合所有技能相关适配器
 */
public class SkillFragment extends BaseTabListFragment {


    public SkillFragment() {
        // Required empty public constructor
        addAdapter(new AllSkillFragmentAdapter());
        addAdapter(new TypeSkillFragmentAdapter());
    }




}
