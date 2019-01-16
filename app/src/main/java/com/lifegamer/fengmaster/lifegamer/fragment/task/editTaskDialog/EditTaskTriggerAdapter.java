package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.adapter.base.AbsBaseRecyclerViewAdapter;
import com.lifegamer.fengmaster.lifegamer.adapter.base.BindingHolder;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;

import java.util.List;

import butterknife.BindView;

/**
 * 显示任务相关触发器的适配器
 * Created by FengMaster on 19/01/15.
 */
public class EditTaskTriggerAdapter extends AbsBaseRecyclerViewAdapter<EditTaskTriggerAdapter.Holder,TriggerInfo> {

    private List<TriggerInfo> triggerInfos;

    private OnItemSelectListener<TriggerInfo> listener;

    public EditTaskTriggerAdapter(List<TriggerInfo> triggerInfos) {
        this.triggerInfos = triggerInfos;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_base_trigger, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setTriggerInfo(triggerInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return triggerInfos.size();
    }

    @Override
    public void addItemSelectListener(OnItemSelectListener<TriggerInfo> listener) {
        this.listener=listener;
    }

    @Override
    public void removeItemSelectListener(OnItemSelectListener<TriggerInfo> listener) {
        this.listener=null;
    }

    private void notifyListener(TriggerInfo triggerInfo){
        if (listener!=null){
            listener.onItemSelect(triggerInfo);
        }
    }

    @Override
    public String getName() {
        return "";
    }


    public class Holder extends BindingHolder implements View.OnClickListener {


        private TriggerInfo triggerInfo;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public TriggerInfo getTriggerInfo() {
            return triggerInfo;
        }

        public void setTriggerInfo(TriggerInfo triggerInfo) {
            this.triggerInfo = triggerInfo;
            setBinding(BR.triggerInfo, triggerInfo);
        }

        @Override
        public void onClick(View view) {
            //请求显示选择框
            notifyListener(triggerInfo);
        }
    }
}
