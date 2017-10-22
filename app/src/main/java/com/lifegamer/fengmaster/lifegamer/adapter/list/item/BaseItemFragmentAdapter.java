package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.BaseRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import java.util.List;

/**
 * Created by qianzise on 2017/10/22.
 */

public abstract class BaseItemFragmentAdapter extends BaseRecyclerViewAdapter<BaseItemFragmentAdapter.Holder, Item> {

    private List<Item> showItemList;

    public abstract void updateItemsList();

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<Item> listener) {

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Item> listener) {

    }

    public class Holder extends BindingHolder{

    public Holder(View itemView) {
        super(itemView);
    }
}

}
