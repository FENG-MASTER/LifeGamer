package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog.event.AddButtonClick;
import com.lifegamer.fengmaster.lifegamer.fragment.trigger.EditTriggerDialog;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.wight.MyItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianzise on 2017/10/19.
 */

public class EditTaskRewardPunishFragment extends EditTaskDialog.SaveableFragment implements OnItemSelectListener<TriggerInfo> {

    @BindView(R.id.rv_dialog_edit_triggers_list)
    RecyclerView triggerRecyclerView;

    private EditTaskTriggerAdapter adapter;

    private Task task;

    private List<TriggerInfo> triggerInfoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_task_reward_punish, container, false);

        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }




    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        adapter=new EditTaskTriggerAdapter(triggerInfoList);
        adapter.addItemSelectListener(this);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        triggerRecyclerView.addItemDecoration(new MyItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    }


    public EditTaskRewardPunishFragment setTask(Task task) {
        this.task = task;
        triggerInfoList=Stream.of(task.getTriggers()).map(Trigger::getTriggerInfo).collect(Collectors.toList());
        return this;
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_reward_punish_tab);
    }


    @Override
    boolean save() {
        task.setTriggers(triggerInfoList);
        return true;
    }

    @Override
    public void onItemSelect(TriggerInfo triggerInfo) {
        EditTriggerDialog dialog=new EditTriggerDialog();
        dialog.setTriggerInfo(triggerInfo);
        dialog.show(getFragmentManager(),"");
    }

    private void addTrigger(){
        //新增触发器
        TriggerInfo triggerInfo=new TriggerInfo();
        EditTriggerDialog dialog=new EditTriggerDialog();
        dialog.setTriggerInfo(triggerInfo);
        dialog.addItemSelectListener(ti -> {
            triggerInfoList.add(ti);
            adapter=new EditTaskTriggerAdapter(triggerInfoList);
            triggerRecyclerView.setAdapter(adapter);

        });
        dialog.show(getFragmentManager(),"");
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void addButtonClick(AddButtonClick addButtonClick){
        addTrigger();
    }
}
