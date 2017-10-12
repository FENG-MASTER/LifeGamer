package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.AllSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.TypeSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 技能fragment
 *
 * 属于高层模块,负责整合所有技能相关适配器
 */
public class SkillFragment extends BaseTabListFragment implements BaseRecyclerViewAdapter.OnItemSelectListener<Skill> {


    public SkillFragment() {
        // Required empty public constructor
        AllSkillFragmentAdapter allSkillFragmentAdapter = new AllSkillFragmentAdapter();
        allSkillFragmentAdapter.addItemSelectListener(this);
        addAdapter(allSkillFragmentAdapter);
        addAdapter(new TypeSkillFragmentAdapter());
    }


    @Override
    public void onItemSelect(Skill skill) {
        ViewUtil.showToast(skill.getName());
        SelectDialog selectDialog=new SelectDialog();

        List<SelectDialog.SelectItem> list=new ArrayList<>();
        list.add(new SelectDialog.SelectItem("111", R.drawable.test1));
        list.add(new SelectDialog.SelectItem("222", R.drawable.test1));
        list.add(new SelectDialog.SelectItem("333", R.drawable.test1));

        selectDialog.setItems(list).show(getFragmentManager(),"select");
    }
}
