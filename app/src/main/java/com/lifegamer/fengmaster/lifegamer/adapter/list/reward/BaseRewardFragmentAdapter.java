package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import android.support.v7.widget.RecyclerView;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.event.reward.DeleteRewardEvent;
import com.lifegamer.fengmaster.lifegamer.event.reward.NewRewardEvent;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qianzise on 2017/10/21.
 */

public abstract class BaseRewardFragmentAdapter extends BaseRecyclerViewAdapter< RewardItem> {


    public BaseRewardFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }



    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newReward(NewRewardEvent newRewardEvent) {
        updateAdapterList();
        notifyDataSetChanged();
    }

    /**
     * 有商品被删除,需要刷新显示
     * @param deleteRewardEvent 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void deleteEntityEvent(DeleteRewardEvent deleteRewardEvent) {
        updateAdapterList();
        notifyDataSetChanged();
    }



    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_reward;
    }

    @Override
    public int getBindingItemID() {
        return BR.reward;
    }
}
