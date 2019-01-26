package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskTimeBinding;
import com.lifegamer.fengmaster.lifegamer.model.Task;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.util.Calendar;

/**
 * Created by qianzise on 2017/10/16.
 * <p>
 * 任务编辑对话框 时间部分
 */

public class EditTaskTimeFragment extends EditTaskDialog.SaveableFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private final static int ONCE = 0;
    private final static int NO_LIMIT = 1;
    private final static int DAYILY=3;

    private DialogEditTaskTimeBinding binding;

    private Task task;

    private Calendar taskTime = Calendar.getInstance();


    public EditTaskTimeFragment setTask(Task task) {
        this.task = task;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogEditTaskTimeBinding.inflate(inflater);

        binding.setTask(task);

        if (task.getExpirationTime() != null) {
            // 非新建任务的时候,获取时间
            taskTime.setTime(task.getExpirationTime());
        }
        //设置重复类型显示
        binding.spDialogEditTaskRepType.setSelection(task.getRepeatType());

        binding.spDialogEditTaskRepType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                binding.layoutTaskTimeRepInterval.setVisibility(View.GONE);
                binding.btDialogEditTaskExpirationTime.setVisibility(View.VISIBLE);
                binding.switchDialogEditTaskAutoFail.setEnabled(true);

                switch (i) {
                    case ONCE:
                        //单次任务
                        binding.etDialogEditTaskRepTimes.setText("1");
                        binding.etDialogEditTaskRepTimes.setEnabled(false);
                        binding.switchDialogEditTaskInfinite.setChecked(false);
                        break;
                    case NO_LIMIT:
                        //如果选中了不限次数任务,不允许设置自动失败

                        binding.etDialogEditTaskRepTimes.setText("-1");
                        binding.etDialogEditTaskRepTimes.setEnabled(false);
                        binding.switchDialogEditTaskAutoFail.setChecked(false);
                        binding.switchDialogEditTaskAutoFail.setEnabled(false);
                        binding.switchDialogEditTaskInfinite.setChecked(true);
                        binding.btDialogEditTaskExpirationTime.setVisibility(View.GONE);
                        break;
                    default:
                        binding.etDialogEditTaskRepTimes.setEnabled(true);
                        binding.switchDialogEditTaskInfinite.setEnabled(true);
                        binding.layoutTaskTimeRepInterval.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        binding.switchDialogEditTaskInfinite.setOnCheckedChangeListener((compoundButton, b) -> {
            //无限完成次数
            if (b) {
                binding.etDialogEditTaskRepTimes.setText("-1");
                binding.etDialogEditTaskRepTimes.setEnabled(false);
                if (binding.spDialogEditTaskRepType.getSelectedItemId()==ONCE){
                    //如果在单次任务下切换无限,则为不限次数任务模式
                    binding.spDialogEditTaskRepType.setSelection(NO_LIMIT,true);
                }
            } else {
                binding.etDialogEditTaskRepTimes.setText("1");
                binding.etDialogEditTaskRepTimes.setEnabled(false);
                binding.spDialogEditTaskRepType.setSelection(DAYILY,true);
            }
        });



        binding.switchDialogEditTaskAutoFail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked&&binding.spDialogEditTaskRepType.getSelectedItemPosition()==NO_LIMIT){
                    binding.spDialogEditTaskRepType.setSelection(0,true);
                }
            }
        });

        binding.btDialogEditTaskExpirationTime.setOnClickListener(this);


        return binding.getRoot();

    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_time_tab);
    }

    @Override
    boolean save() {
        //可完成次数
        task.setRepeatAvailableTime(Integer.valueOf(binding.etDialogEditTaskRepTimes.getText().toString()));
        task.setRepeatType(binding.spDialogEditTaskRepType.getSelectedItemPosition());

        if (task.getRepeatType()==Task.REP_CONTINUOUS){
            //无限次数任务,过期时间会设置为无效
            task.setExpirationTime(Task.noDate);
        }else {
            //过期时间
            task.setExpirationTime(taskTime.getTime());
        }



        //重复类型

        task.setRepeatInterval(Integer.valueOf(binding.etDialogEditTaskRepInterval.getText().toString()));
        task.setAutoFail(binding.switchDialogEditTaskAutoFail.isChecked());

         return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_dialog_edit_task_expiration_time) {
            //选择过期时间
            //弹出对话框
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                    taskTime.get(Calendar.YEAR),
                    taskTime.get(Calendar.MONTH),
                    taskTime.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();

        }


    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        taskTime.set(Calendar.YEAR, year);
        taskTime.set(Calendar.MONTH, monthOfYear);
        taskTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this,
                taskTime.get(Calendar.HOUR_OF_DAY),
                taskTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        taskTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        taskTime.set(Calendar.MINUTE, minute);

        //更新过期时间显示
        setTaskExpirationView();
    }

    /**
     * 更新过期时间显示
     */
    private void setTaskExpirationView() {
        binding.btDialogEditTaskExpirationTime.setText(FormatUtil.date2Str(taskTime.getTime()));
    }
}
