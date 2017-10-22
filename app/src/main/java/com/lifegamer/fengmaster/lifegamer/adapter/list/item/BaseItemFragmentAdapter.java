package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.adapter.list.reward.BaseRewardFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.databinding.ItemBaseItemBinding;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 基础物品显示适配器
 */

public abstract class BaseItemFragmentAdapter extends BaseRecyclerViewAdapter<BaseItemFragmentAdapter.Holder, Item> {

    protected List<Item> showItemList;

    private List<OnItemSelectListener<Item>> listeners = new ArrayList<>();

    public abstract void updateItemsList();


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showItemList==null){
            updateItemsList();
        }
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setItem(showItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return showItemList.size();
    }


    @Override
    public void addItemSelectListener(OnItemSelectListener<Item> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Item> listener) {
        listeners.remove(listener);
    }

    public class Holder extends BindingHolder {

        private Item item;

        public void setItem(Item item) {
            this.item = item;
            binding.setVariable(BR.item,item);
        }

        public Holder(View itemView) {
            super(itemView);
        }
    }

}
