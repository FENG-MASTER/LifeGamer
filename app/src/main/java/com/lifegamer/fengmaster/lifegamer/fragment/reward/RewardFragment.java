package com.lifegamer.fengmaster.lifegamer.fragment.reward;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.AllAvailableRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.AllRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.PurchasableRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.TypeRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.command.command.reward.BuyRewardCommand;
import com.lifegamer.fengmaster.lifegamer.command.command.reward.DeleteRewardCommand;
import com.lifegamer.fengmaster.lifegamer.event.reward.NewRewardEvent;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.util.PreferenceUtil;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;
import com.lifegamer.fengmaster.lifegamer.wight.model.SelectItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
      super();
        if (PreferenceUtil.checkIfShow(TypeRewardFragmentAdapter.class.getSimpleName())){
            List<String> skillType = Game.getInstance().getRewardManager().getAllType();
            for (String type : skillType) {
                TypeRewardFragmentAdapter typeRewardFragmentAdapter = new TypeRewardFragmentAdapter(type);
                typeRewardFragmentAdapter.addItemSelectListener(this);
                addAdapter(typeRewardFragmentAdapter);
            }
        }
    }

    @Override
    public void onActionButtonClick() {
        EditRewardDialog dialog=new EditRewardDialog();
        dialog.setRewardItem(new RewardItem());
        dialog.show(getChildFragmentManager(),"editReward");
    }

    @Override
    public Class[] getAdapterClasses() {
        return new Class[]{AllRewardFragmentAdapter.class,AllAvailableRewardFragmentAdapter.class,PurchasableRewardFragmentAdapter.class};
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
            case SelectItem.BUY_ID:
                //购买物品
                Game.getInstance().getCommandManager().executeCommand(new BuyRewardCommand(rewardItem));
                break;
            case SelectItem.DELETE_ID:
                //删除
                Game.getInstance().getCommandManager().executeCommand(new DeleteRewardCommand(rewardItem));
                break;
            default:
        }
    }

    private void onRewardItemSelect(RewardItem rewardItem) {
        this.rewardItem=rewardItem;
        SelectDialog dialog=new SelectDialog();
        List<SelectItem> list=new ArrayList<>();
        if (rewardItem.isPurchasable()){
            list.add(SelectItem.BUY);
        }
        list.add(SelectItem.EDIT);
        list.add(SelectItem.DELETE);
        dialog.setItems(list);
        dialog.addItemSelectListener(this);
        dialog.show(getChildFragmentManager(),"select");
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void tabChange(NewRewardEvent newRewardEvent){
        notifyTabChange();
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
}
