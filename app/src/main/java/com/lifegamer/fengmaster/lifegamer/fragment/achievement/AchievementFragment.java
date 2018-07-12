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
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.GotAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.LoseAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.achievement.UpdateAchievementCommand;
import com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

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
        achievementFragmentAdapters.add(new AllAchievementFragmentAdapter());
        achievementFragmentAdapters.add(new GotAchievementFragmentAdapter());

        Stream.of(achievementFragmentAdapters).forEach(baseAchievementFragmentAdapter -> {
            addAdapter(baseAchievementFragmentAdapter);
            baseAchievementFragmentAdapter.addItemSelectListener(AchievementFragment.this);
        });

    }


    @Override
    public void onActionButtonClick() {
        EditAchievementDialog dialog = new EditAchievementDialog();
        dialog.show(getChildFragmentManager(), "editAchievement");
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
            default:

        }

    }

    private void gotAchievement() {

        Game.getInstance().getCommandManager().executeCommand(new GotAchievementCommand(selectAchievement));
    }

    private void loseAchievement() {

        Game.getInstance().getCommandManager().executeCommand(new LoseAchievementCommand(selectAchievement));
    }
}
