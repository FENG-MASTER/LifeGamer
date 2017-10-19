package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.databinding.DialogEditTaskTimeBinding;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by qianzise on 2017/10/16.
 */

public class EditTaskTimeFragment extends EditTaskDialog.SaveableFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogEditTaskTimeBinding binding=DialogEditTaskTimeBinding.inflate(inflater);
        binding.setTask(new Task());


        return binding.getRoot();

    }

    @Override
    public String getName() {
        return "时间";
    }

    @Override
    void save() {

    }
}
