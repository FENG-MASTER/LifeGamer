package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.event.task.DeleteTaskEvent;
import com.lifegamer.fengmaster.lifegamer.event.task.NewTaskEvent;
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

public abstract class BaseTaskFragmentAdapter extends AbsBaseRecyclerViewAdapter<BaseTaskFragmentAdapter.Holder,Task> {
    //点击事件监听者
    private List<OnItemSelectListener<Task>> listeners=new LinkedList<>();


    /**
     * 显示任务列表
     */
    protected List<Task> showTaskList;

    /**
     * 更新任务显示列表方法
     */
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
        holder.setTask(showTaskList.get(position));
    }

    @Override
    public int getItemCount() {
        return showTaskList.size();
    }



    @Override
    public void addItemSelectListener(OnItemSelectListener<Task> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<Task> listener) {
        listeners.remove(listener);
    }

    private void notifyListener(Task task) {
        for (OnItemSelectListener<Task> listener : listeners) {
            listener.onItemSelect(task);
        }
    }

    public class Holder extends BindingHolder implements View.OnClickListener {

        /**
         * 任务对象
         */
        private Task task;


        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setTask(Task task) {
            this.task = task;
            setBinding(BR.task,task);
        }

        @Override
        public void onClick(View v) {
            notifyListener(task);
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


    /**
     * 有任务删除,需要刷新
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void deltedTask(DeleteTaskEvent event){
        updateTaskList();
        notifyDataSetChanged();
    }
}
