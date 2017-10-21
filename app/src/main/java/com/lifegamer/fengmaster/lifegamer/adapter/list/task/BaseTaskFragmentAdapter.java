package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;

/**
 * Created by qianzise on 2017/10/15.
 */

public abstract class BaseTaskFragmentAdapter extends BaseRecyclerViewAdapter<BaseTaskFragmentAdapter.Holder,Task>{

    protected List<Task> showTaskList;

    public abstract void updateTaskList();

    public BaseTaskFragmentAdapter() {
        EventBus.getDefault().register(this);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_base_task, parent, false);
        return new Holder(inflate);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setBinding(BR.task,showTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return showTaskList.size();
    }



    @Override
    public void addItemSelectListener(OnItemSelectListener<Task> listener) {

    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Task> listener) {

    }

    public static class Holder extends BindingHolder {


        public Holder(View itemView) {
            super(itemView);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (showTaskList==null){
            updateTaskList();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        EventBus.getDefault().unregister(this);
    }

    /**
     * 有新建的任务,需要刷新
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void newTaskCome(NewTaskEvent event){
        updateTaskList();
        notifyDataSetChanged();
    }
}
