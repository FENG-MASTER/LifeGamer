package com.lifegamer.fengmaster.lifegamer.fragment.skill;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.AllSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.skill.TypeSkillFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.skill.RemoveSkillCommand;
import com.lifegamer.fengmaster.lifegamer.event.skill.NewSkillEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.util.ViewUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;
import com.umeng.commonsdk.debug.E;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 能力fragment
 * <p>
 * 属于高层模块,负责整合所有能力相关适配器和相关子fragment
 */
public class SkillFragment extends BaseTabListFragment implements OnItemSelectListener {
    private Skill selectSkill;

    public SkillFragment() {
        super();
        if (PreferenceUtil.checkIfShow(TypeSkillFragmentAdapter.class.getSimpleName())){
            List<String> skillType = Game.getInstance().getSkillManager().getAllSkillType();
            for (String type : skillType) {
                TypeSkillFragmentAdapter typeSkillFragmentAdapter = new TypeSkillFragmentAdapter(type);
                typeSkillFragmentAdapter.addItemSelectListener(this);
                addAdapter(typeSkillFragmentAdapter);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActionButtonClick() {
        //新建能力
        EditSkillDialog dialog=new EditSkillDialog();
        dialog.show(getChildFragmentManager(),"1");
    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllSkillFragmentAdapter.class};
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


    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void tabChange(NewSkillEvent newSkillEvent){
        notifyTabChange();
    }
}
