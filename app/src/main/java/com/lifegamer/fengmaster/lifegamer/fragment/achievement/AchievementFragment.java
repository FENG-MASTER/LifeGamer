package com.lifegamer.fengmaster.lifegamer.fragment.achievement;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.AllAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.BaseAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet.GotAchievementFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *成就fragment
 *
 * 属于高层模块,负责整合所有成就相关适配器和相关子fragment
 */
public class AchievementFragment extends BaseTabListFragment implements OnItemSelectListener {
    private List<BaseAchievementFragmentAdapter> achievementFragmentAdapters=new ArrayList<>();

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
        EditAchievementDialog dialog=new EditAchievementDialog();
        dialog.show(getChildFragmentManager(),"editAchievement");
    }

    @Override
    public void onItemSelect(Object o) {
        if(o instanceof Achievement){
            onAchievementSelect((Achievement) o);
        }else {
            onSelectItemSelect((SelectItem) o);
        }

    }

    /**
     * 点击成就
     * @param achievement
     */
    private void onAchievementSelect(Achievement achievement){
        SelectDialog dialog = new SelectDialog();
        List<SelectItem> itemList = new ArrayList<>();
        itemList.add(SelectItem.GOT);
        itemList.add(SelectItem.GOT);
        dialog.setItems(itemList);
        dialog.addItemSelectListener(this);
        dialog.show(getFragmentManager(), "select");


    }

    /**
     * 选择菜单项被选中
     * @param selectItem
     */
    private void onSelectItemSelect(SelectItem selectItem){


    }
}
