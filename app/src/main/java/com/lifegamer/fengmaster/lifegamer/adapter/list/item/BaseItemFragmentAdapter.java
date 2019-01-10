package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.event.item.UpdateItemEvent;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 基础物品显示适配器
 */

public abstract class BaseItemFragmentAdapter extends AbsBaseRecyclerViewAdapter<BaseItemFragmentAdapter.Holder, Item> {

    protected List<Item> showItemList;

    private List<OnItemSelectListener<Item>> listeners = new ArrayList<>();

    public abstract void updateItemsList();

    public BaseItemFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

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

    /**
     * 从父RecyclerView中收回的时候调用
     * @param recyclerView 父
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        //内存泄漏
        EventBus.getDefault().unregister(this);
    }

    private void notifyListener(Item item) {
        for (OnItemSelectListener<Item> listener : listeners) {
            listener.onItemSelect(item);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void itemUpdate(UpdateItemEvent event){
        updateItemsList();
    }

    public class Holder extends BindingHolder implements View.OnClickListener {

        private Item item;

        public void setItem(Item item) {
            this.item = item;
            binding.setVariable(BR.item,item);
        }

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyListener(item);
        }
    }

}
