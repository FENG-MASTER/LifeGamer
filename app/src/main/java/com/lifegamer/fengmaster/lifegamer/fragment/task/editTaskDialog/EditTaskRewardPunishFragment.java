package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.base.OnItemSelectListener;
import com.lifegamer.fengmaster.lifegamer.fragment.trigger.EditTriggerDialog;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.model.TriggerInfo;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.wight.MyItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qianzise on 2017/10/19.
 */

public class EditTaskRewardPunishFragment extends EditTaskDialog.SaveableFragment implements OnItemSelectListener<TriggerInfo> {

    @BindView(R.id.rv_dialog_edit_triggers_list)
    RecyclerView triggerRecyclerView;

    private TriggerInfo editTrigger;

    private EditTaskTriggerAdapter adapter;

    private Task task;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_task_reward_punish, container, false);

        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter=new EditTaskTriggerAdapter(Stream.of(task.getTriggers()).map(trigger -> trigger.getTriggerInfo()).collect(Collectors.toList()));
        adapter.addItemSelectListener(this);
        triggerRecyclerView.setAdapter(adapter);
        triggerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        triggerRecyclerView.addItemDecoration(new MyItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

    }


    public EditTaskRewardPunishFragment setTask(Task task) {
        this.task = task;
        return this;
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_reward_punish_tab);
    }


    @Override
    boolean save() {
        //是否当前编辑的触发器已经task中,如果不存在,需要和task关联
        boolean match = Stream.of(task.getTriggers()).map(trigger -> trigger.getTriggerInfo()).anyMatch(value -> value.equals(editTrigger));
        if (!match) {
            task.addTrigger(editTrigger);
        }
        return true;
    }

    @Override
    public void onItemSelect(TriggerInfo triggerInfo) {
        editTrigger=triggerInfo;
        EditTriggerDialog dialog=new EditTriggerDialog();
        dialog.setTriggerInfo(triggerInfo);
        dialog.show(getFragmentManager(),"");
    }

    @OnClick(R.id.bt_dialog_edit_trigger_add_trigger)
    public void addTrigger(View view){
        //新增触发器
        TriggerInfo triggerInfo=new TriggerInfo();
        editTrigger=triggerInfo;
        EditTriggerDialog dialog=new EditTriggerDialog();
        dialog.setTriggerInfo(triggerInfo);
        dialog.show(getFragmentManager(),"");
    }
}
