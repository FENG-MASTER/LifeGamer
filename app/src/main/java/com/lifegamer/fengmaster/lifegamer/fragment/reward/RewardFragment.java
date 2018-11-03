package com.lifegamer.fengmaster.lifegamer.fragment.reward;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.AllAvailableRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.AllRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import java.util.ArrayList;
import java.util.List;

/**
 *奖励fragment
 *
 *  属于高层模块,负责整合所有奖励相关适配器和相关子fragment
 */
public class RewardFragment extends BaseTabListFragment implements OnItemSelectListener {

    private RewardItem rewardItem;

    public RewardFragment() {
        AllRewardFragmentAdapter allRewardFragmentAdapter = new AllRewardFragmentAdapter();
        AllAvailableRewardFragmentAdapter allAvailableRewardFragmentAdapter=new AllAvailableRewardFragmentAdapter();
        allAvailableRewardFragmentAdapter.addItemSelectListener(this);
        allRewardFragmentAdapter.addItemSelectListener(this);
        addAdapter(allRewardFragmentAdapter);
        addAdapter(allAvailableRewardFragmentAdapter);

    }

    @Override
    public void onActionButtonClick() {
        EditRewardDialog dialog=new EditRewardDialog();
        dialog.setRewardItem(new RewardItem());
        dialog.show(getChildFragmentManager(),"editReward");
    }

    @Override
    public void onItemSelect(Object o) {
        if (o instanceof RewardItem){
            onRewardItemSelect((RewardItem)o);
        }else if (o instanceof SelectItem){
            onSelectItemSelect((SelectItem)o);
        }

    }

    private void onSelectItemSelect(SelectItem selectItem) {
        switch (selectItem.getId()){
            case SelectItem.EDIT_ID:
                //编辑
                EditRewardDialog dialog=new EditRewardDialog();
                dialog.setRewardItem(rewardItem);
                dialog.show(getFragmentManager(),"editReward");
                break;
            default:
        }
    }

    private void onRewardItemSelect(RewardItem rewardItem) {
        this.rewardItem=rewardItem;
        SelectDialog dialog=new SelectDialog();
        List<SelectItem> list=new ArrayList<>();
        list.add(SelectItem.EDIT);
        list.add(SelectItem.DELETE);
        dialog.setItems(list);
        dialog.addItemSelectListener(this);
        dialog.show(getChildFragmentManager(),"select");
    }
}
