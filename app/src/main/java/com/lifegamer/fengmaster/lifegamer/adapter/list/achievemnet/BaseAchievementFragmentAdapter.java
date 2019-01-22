package com.lifegamer.fengmaster.lifegamer.adapter.list.achievemnet;

import android.support.v7.widget.RecyclerView;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.event.achievement.DeleteAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievementEvent;
import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qianzise on 2017/10/23.
 */

public abstract class BaseAchievementFragmentAdapter extends BaseRecyclerViewAdapter<Achievement> {

    public BaseAchievementFragmentAdapter() {
        EventBus.getDefault().register(this);
    }


    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_achievement;
    }

    @Override
    public int getBindingItemID() {
        return BR.achievement;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onGotAchievement(GotAchievementEvent gotAchievementEvent){
        updateAdapterList();
        notifyDataSetChanged();
    }
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onLostAchievement(LostAchievementEvent lostAchievementEvent){
        updateAdapterList();
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onDeleteAchievement(DeleteAchievementEvent deleteAchievementEvent){
        updateAdapterList();
        notifyDataSetChanged();
    }

}