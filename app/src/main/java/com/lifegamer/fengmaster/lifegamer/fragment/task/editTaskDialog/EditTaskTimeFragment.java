package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskTimeBinding;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.util.Calendar;

/**
 * Created by qianzise on 2017/10/16.
 *
 * 任务编辑对话框 时间部分
 */

public class EditTaskTimeFragment extends EditTaskDialog.SaveableFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private DialogEditTaskTimeBinding binding;

    private Task task;

    private Calendar taskTime=Calendar.getInstance();


    public EditTaskTimeFragment setTask(Task task){
        this.task=task;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogEditTaskTimeBinding.inflate(inflater);

        binding.setTask(task);
        taskTime.setTime(task.getExpirationTime());

        //设置重复类型显示
        binding.spDialogEditTaskRepType.setSelection(task.getRepeatType());


        binding.switchDialogEditTaskInfinite.setOnCheckedChangeListener((compoundButton, b) -> {
            //无限完成次数
            if (b){
                binding.etDialogEditTaskRepTimes.setText("-1");
                binding.etDialogEditTaskRepTimes.setEnabled(false);
            }else {
                binding.etDialogEditTaskRepTimes.setEnabled(true);
            }
        });

        binding.btDialogEditTaskExpirationTime.setOnClickListener(this);


        return binding.getRoot();

    }

    @Override
    public String getName() {
        return "时间";
    }

    @Override
    boolean save() {
        //可完成次数
        task.setRepeatAvailableTime(Integer.valueOf(binding.etDialogEditTaskRepTimes.getText().toString()));

        //重复类型

        task.setRepeatType(binding.spDialogEditTaskRepType.getSelectedItemPosition());
        task.setAutoFail(binding.switchDialogEditTaskAutoFail.isChecked());

        //过期时间
        task.setExpirationTime(taskTime.getTime());
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.bt_dialog_edit_task_expiration_time){
            //选择过期时间
            //弹出对话框
            DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),this,
                    taskTime.get(Calendar.YEAR),
                    taskTime.get(Calendar.MONTH),
                    taskTime.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();

        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        taskTime.set(Calendar.YEAR,year);
        taskTime.set(Calendar.MONTH,monthOfYear);
        taskTime.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        TimePickerDialog timePickerDialog=new TimePickerDialog(getContext(), this,
                taskTime.get(Calendar.HOUR_OF_DAY),
                taskTime.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        taskTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
        taskTime.set(Calendar.MINUTE,minute);

        //更新过期时间显示
        setTaskExpirationView();
    }

    /**
     * 更新过期时间显示
     */
    private void setTaskExpirationView(){
        binding.btDialogEditTaskExpirationTime.setText(FormatUtil.date2Str(taskTime.getTime()));
    }
}
