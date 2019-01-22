package com.lifegamer.fengmaster.lifegamer.fragment.achievement;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.AllAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.BaseAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.GotAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.NoGotAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.TypeAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.DeleteAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.GotAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.LoseAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.UpdateAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.NewAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.item.newItemEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * 成就fragment
 * <p>
 * 属于高层模块,负责整合所有成就相关适配器和相关子fragment
 */
public class AchievementFragment extends BaseTabListFragment implements OnItemSelectListener {

    private Achievement selectAchievement;

    private List<BaseAchievementFragmentAdapter> achievementFragmentAdapters = new ArrayList<>();

    public AchievementFragment() {
        super();
        if (PreferenceUtil.checkIfShow(TypeAchievementFragmentAdapter.class.getSimpleName())){
            List<String> allType = Game.getInstance().getAchievementManager().getAllType();
            for (String type : allType) {
                TypeAchievementFragmentAdapter typeSkillFragmentAdapter = new TypeAchievementFragmentAdapter(type);
                typeSkillFragmentAdapter.addItemSelectListener(this);
                addAdapter(typeSkillFragmentAdapter);
            }
        }
    }


    @Override
    public void onActionButtonClick() {
        EditAchievementDialog dialog = new EditAchievementDialog();
        dialog.setAchievement(new Achievement());
        dialog.show(getChildFragmentManager(), "editAchievement");
    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllAchievementFragmentAdapter.class, GotAchievementFragmentAdapter.class, NoGotAchievementFragmentAdapter.class};
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof Achievement) {
            onAchievementSelect((Achievement) o);
        } else {
            onSelectItemSelect((SelectItem) o);
        }

    }

    /**
     * 点击成就
     *
     * @param achievement
     */
    private void onAchievementSelect(Achievement achievement) {
        selectAchievement = achievement;
        SelectDialog dialog = new SelectDialog();
        List<SelectItem> itemList = new ArrayList<>();
        if (achievement.isGot()) {
            itemList.add(SelectItem.LOSE);
        } else {
            itemList.add(SelectItem.GOT);
        }
        itemList.add(SelectItem.EDIT);
        itemList.add(SelectItem.DELETE);
        dialog.setItems(itemList);
        dialog.addItemSelectListener(this);
        dialog.show(getFragmentManager(), "select");


    }

    /**
     * 选择菜单项被选中
     *
     * @param selectItem
     */
    private void onSelectItemSelect(SelectItem selectItem) {
        switch (selectItem.getId()) {
            case SelectItem.GOT_ID:
                //获得成就
                gotAchievement();
                break;
            case SelectItem.LOSE_ID:
                loseAchievement();
                break;
            case SelectItem.EDIT_ID:
                //编辑成就
                if (selectAchievement!=null){
                    EditAchievementDialog dialog=new EditAchievementDialog();
                    dialog.setAchievement(selectAchievement);
                    dialog.show(getFragmentManager(),"select");
                }
                break;
            case SelectItem.DELETE_ID:
                Game.getInstance().getCommandManager().executeCommand(new DeleteAchievementCommand(selectAchievement));
                break;

            default:

        }

    }

    private void gotAchievement() {

        Game.getInstance().getCommandManager().executeCommand(new GotAchievementCommand(selectAchievement));
    }

    private void loseAchievement() {

        Game.getInstance().getCommandManager().executeCommand(new LoseAchievementCommand(selectAchievement));
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

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void tabChange(NewAchievementEvent newAchievementEvent){
        notifyTabChange();
    }
}
