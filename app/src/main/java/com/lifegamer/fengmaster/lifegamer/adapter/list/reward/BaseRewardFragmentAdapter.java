package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/21.
 */

public abstract class BaseRewardFragmentAdapter extends BaseRecyclerViewAdapter<BaseRewardFragmentAdapter.Holder, RewardItem> {

    protected List<RewardItem> rewardItemList;

    public abstract void updateRewardItemList();

    private List<OnItemSelectListener<RewardItem>> listeners = new ArrayList<>();


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (rewardItemList==null){
            updateRewardItemList();
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_reward, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setRewardItem(rewardItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return rewardItemList.size();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<RewardItem> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<RewardItem> listener) {
        listeners.remove(listener);
    }

    private void notifyListener(RewardItem rewardItem) {
        for (OnItemSelectListener<RewardItem> listener : listeners) {
            listener.onItemSelect(rewardItem);
        }
    }

    public class Holder extends BindingHolder implements View.OnClickListener {

        private RewardItem rewardItem;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setRewardItem(RewardItem rewardItem) {
            this.rewardItem = rewardItem;
            setBinding(BR.reward,rewardItem);
        }

        @Override
        public void onClick(View v) {
            notifyListener(rewardItem);
        }
    }
}
