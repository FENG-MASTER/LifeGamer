package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import com.annimon.stream.function.Function;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.task.UpdateTaskCommand;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskExtraBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by qianzise on 2017/10/16.
 */

public class EditTaskExtraFragment extends EditTaskDialog.SaveableFragment implements View.OnClickListener {

    private Task task;

    private List<Task> preTasks=new LinkedList<>();

    private LinearLayout.LayoutParams buttonLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private DialogEditTaskExtraBinding binding;

    public EditTaskExtraFragment() {

    }

    public EditTaskExtraFragment setTask(Task task){
        this.task=task;
        initPreTask();
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditTaskExtraBinding.inflate(inflater);
        binding.setTask(task);

        for (Task preTask : preTasks) {
            Button button=newPreTaskButton(preTask);
            binding.llDialogEditTaskExtraParentTask.addView(button,buttonLayoutParams);
        }

        //新增父任务
        binding.btDialogEditTaskAddPreTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: 弹出选择框
            }
        });

        return binding.getRoot();
    }


    private void initPreTask(){
        preTasks.clear();
        List<Integer> preTaskIDs = task.getPreTasks();
        preTasks.addAll(Stream.of(preTaskIDs).
                map(integer -> Game.getInstance().getTaskManager().getTask(integer)).
                collect(Collectors.toList()));
    }

    private Button newPreTaskButton(Task task){
        Button button=new Button(getContext());
        button.setLayoutParams(buttonLayoutParams);
        button.setText(task.getName());
        button.setOnClickListener(this);
        button.setTag(task);
        button.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_clear_black_24dp,0);
        return button;
    }



    @Override
    public String getName() {
        return "额外";
    }

    @Override
    public void save() {
        task.setFear(binding.sbDialogEditTaskFear.getProgress());
        task.setUrgency(binding.sbDialogEditTaskUrgency.getProgress());
        task.setDifficulty(binding.sbDialogEditTaskDifficulty.getProgress());

        task.setPreTasks(Stream.of(preTasks).map(task1 -> (int) task1.getId()).collect(Collectors.toList()));

        Game.getInstance().getCommandManager().executeCommand(new UpdateTaskCommand(task));
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button&&view.getTag()!=null){
            //按下取消某父任务
            Task t= (Task) view.getTag();
            preTasks.remove(t);
            ((LinearLayout)view.getParent()).removeView(view);
        }

    }
}
