package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.event.task.DeleteTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.UpdateTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 基础 任务显示适配器
 */

public abstract class BaseTaskFragmentAdapter extends BaseRecyclerViewAdapter<Task> {


    public BaseTaskFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public int getItemLayoutID() {
        return R.layout.item_base_task;
    }

    @Override
    public int getBindingItemID() {
        return BR.task;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 有新建的任务,需要刷新
     * @param newTaskEvent 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newTaskEvent(NewTaskEvent newTaskEvent) {
        updateShowList();
        notifyDataSetChanged();
    }

    /**
     * 有任务被删除,需要刷新显示
     * @param deleteTaskEvent 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void deleteEntityEvent(DeleteTaskEvent deleteTaskEvent) {
        updateShowList();
        notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void updateEntityEvent(UpdateTaskEvent updateTaskEvent) {
        updateShowList();
        notifyDataSetChanged();
    }
}
