package com.lifegamer.fengmaster.lifegamer.fragment;


import android.support.v4.app.Fragment;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.AllSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.TypeSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 技能fragment
 * <p>
 * 属于高层模块,负责整合所有技能相关适配器
 */
public class SkillFragment extends BaseTabListFragment implements OnItemSelectListener {


    public SkillFragment() {
        // Required empty public constructor
        AllSkillFragmentAdapter allSkillFragmentAdapter = new AllSkillFragmentAdapter();
        allSkillFragmentAdapter.addItemSelectListener(this);
        addAdapter(allSkillFragmentAdapter);
        addAdapter(new TypeSkillFragmentAdapter());
    }


    @Override
    public void onItemSelect(Object obj) {
        if (obj instanceof Skill){
            onSkillSelect((Skill) obj);
        }else if (obj instanceof SelectItem){
            onSelectItemSelect((SelectItem) obj);
        }
    }

    @SuppressWarnings("all")
    private void onSkillSelect(Skill skill){

        ViewUtil.showToast(skill.getName());
        SelectDialog selectDialog = new SelectDialog();

        selectDialog.addItemSelectListener(this);

        List<SelectItem> list = new ArrayList<>();
        list.add(SelectItem.EDIT);
        list.add(SelectItem.DELETE);
        list.add(SelectItem.NOTES);

        selectDialog.setItems(list).show(getFragmentManager(), "select");
    }


    private void onSelectItemSelect(SelectItem item){

        switch (item.getId()){
            case SelectItem.FINISH_ID:
                //完成

                break;
        }

        ViewUtil.showToast(item.getName());

    }
}
