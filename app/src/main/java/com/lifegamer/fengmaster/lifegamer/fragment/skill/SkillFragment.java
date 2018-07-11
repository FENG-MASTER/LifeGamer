package com.lifegamer.fengmaster.lifegamer.fragment.skill;


import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.AllSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.TypeSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.RemoveSkillCommand;
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
 * 属于高层模块,负责整合所有技能相关适配器和相关子fragment
 */
public class SkillFragment extends BaseTabListFragment implements OnItemSelectListener {
    private Skill selectSkill;

    public SkillFragment() {
        // Required empty public constructor
        AllSkillFragmentAdapter allSkillFragmentAdapter = new AllSkillFragmentAdapter();
        allSkillFragmentAdapter.addItemSelectListener(this);
        addAdapter(allSkillFragmentAdapter);

        List<String> skillType = Game.getInstance().getSkillManager().getAllSkillType();
        for (String type : skillType) {
            TypeSkillFragmentAdapter typeSkillFragmentAdapter = new TypeSkillFragmentAdapter(type);
            typeSkillFragmentAdapter.addItemSelectListener(this);
            addAdapter(typeSkillFragmentAdapter);
        }




    }

    @Override
    public void onActionButtonClick() {
        //新建技能
        EditSkillDialog dialog=new EditSkillDialog();
        dialog.show(getChildFragmentManager(),"1");
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
        selectSkill=skill;

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
            case SelectItem.EDIT_ID:
                //编辑
                if (selectSkill!=null){
                    EditSkillDialog dialog=new EditSkillDialog();
                    dialog.setSkill(selectSkill);
                    dialog.show(getFragmentManager(),"select");
                }
                break;
            case SelectItem.DELETE_ID:
                //删除
                if (selectSkill!=null){
                    Game.getInstance().getCommandManager().executeCommand(new RemoveSkillCommand(selectSkill));
                }

                break;
            default:

        }

        ViewUtil.showToast(item.getName());

    }
}
