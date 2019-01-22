package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import android.support.v7.widget.RecyclerView;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.event.item.newItemEvent;
import com.lifegamer.fengmaster.lifegamer.event.item.DeleteItemEvent;
import com.lifegamer.fengmaster.lifegamer.event.item.UpdateItemEvent;
import com.lifegamer.fengmaster.lifegamer.model.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 基础物品显示适配器
 */

public abstract class BaseItemFragmentAdapter extends BaseRecyclerViewAdapter<Item> {



    public BaseItemFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showList==null){
            updateAdapterList();
        }
    }


    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_item;
    }

    @Override
    public int getBindingItemID() {
        return BR.item;
    }


    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void itemUpdate(UpdateItemEvent event){
        updateAdapterList();
        notifyDataSetChanged();

    }

    /**
     * 有物品被删除,需要刷新显示
     * @param deleteItemEvent 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void deleteEntityEvent(DeleteItemEvent deleteItemEvent) {
        updateAdapterList();
        notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newEntityEvent(newItemEvent newItemEvent) {
        updateAdapterList();
        notifyDataSetChanged();
    }



}
