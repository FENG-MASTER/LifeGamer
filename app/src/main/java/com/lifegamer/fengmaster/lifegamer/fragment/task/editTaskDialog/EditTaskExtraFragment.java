package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskExtraBinding;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.task.BaseTaskXPCal;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.task.ITaskXPCal;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by qianzise on 2017/10/16.
 * <p>
 * 任务编辑对话框 额外部分
 */

public class EditTaskExtraFragment extends EditTaskDialog.SaveableFragment implements View.OnClickListener {

    private Task task;


    /**
     * TODO:更多的经验值计算器
     */
    private ITaskXPCal taskXPCal = new BaseTaskXPCal();

    private List<Task> preTasks = new LinkedList<>();

    private LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private DialogEditTaskExtraBinding binding;

    public EditTaskExtraFragment() {

    }

    public EditTaskExtraFragment setTask(Task task) {
        this.task = task;
        initPreTask();
        return this;
    }

    private void initPreTask() {
        preTasks.clear();
        List<Integer> preTaskIDs = task.getPreTasks();
        if (preTaskIDs == null) {
            return;
        }
        preTasks.addAll(Stream.of(preTaskIDs).
                map(integer -> Game.getInstance().getTaskManager().getTask(integer)).
                collect(Collectors.toList()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogEditTaskExtraBinding.inflate(inflater);
        binding.setTask(task);

        setValListener();

        //初始化原有的父任务
        for (Task preTask : preTasks) {
            addPreTask(preTask);
        }

        //新增父任务
        binding.btDialogEditTaskAddPreTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出选择框

                //除去已经添加的父任务和自身
                List<Task> tasks = Stream.of(Game.getInstance().getTaskManager().getAllTask()).
                        filterNot(value -> preTasks.contains(value)).
                        filterNot(value -> value == task).
                        collect(Collectors.toList());

                List<String> names = Stream.of(tasks).
                        map(Task::getName).collect(Collectors.toList());

                //选择框显示
                new AlertDialog.Builder(getContext()).setSingleChoiceItems(names.toArray(new String[names.size()]), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preTasks.add(tasks.get(i));
                        addPreTask(tasks.get(i));
                        dialogInterface.dismiss();
                    }
                }).create().show();

            }
        });

        return binding.getRoot();
    }

    /**
     * 设置三个进度条的进度监听,用于显示数值
     */
    private void setValListener() {
        binding.sbDialogEditTaskDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.tvDialogEditTaskExtraDifficulty.setText(getString(R.string.difficulty_num) + i);
                updateXP();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.sbDialogEditTaskFear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.tvDialogEditTaskExtraFear.setText(getString(R.string.fear_num) + i);
                updateXP();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.sbDialogEditTaskUrgency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.tvDialogEditTaskExtraUrgency.setText(getString(R.string.urgency_num) + i);
                updateXP();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void addPreTask(Task task) {
        Button button = newPreTaskButton(task);
        binding.llDialogEditTaskExtraParentTask.addView(button, buttonLayoutParams);
    }

    private Button newPreTaskButton(Task task) {
        Button button = new Button(getContext());
        button.setLayoutParams(buttonLayoutParams);
        button.setText(task.getName());
        button.setOnClickListener(this);
        button.setTag(task);
        button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_clear_black_24dp, 0);
        return button;
    }

    private void updateXP() {
        binding.tvDialogEditTaskExtraXp.setText(
                getString(R.string.task_get_xp) + (taskXPCal.calXP(
                binding.sbDialogEditTaskDifficulty.getProgress(),
                binding.sbDialogEditTaskFear.getProgress(),
                binding.sbDialogEditTaskUrgency.getProgress())));
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_extra_tab);
    }

    @Override
    public boolean save() {
        task.setFear(binding.sbDialogEditTaskFear.getProgress());
        task.setUrgency(binding.sbDialogEditTaskUrgency.getProgress());
        task.setDifficulty(binding.sbDialogEditTaskDifficulty.getProgress());
        task.setXp(taskXPCal.calXP(task.getDifficulty(), task.getFear(), task.getUrgency()));

        task.setPreTasks(Stream.of(preTasks).map(task1 -> (int) task1.getId()).collect(Collectors.toList()));

        return true;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button && view.getTag() != null) {
            //按下取消某父任务
            Task t = (Task) view.getTag();
            preTasks.remove(t);
            ((LinearLayout) view.getParent()).removeView(view);
        }

    }
}
