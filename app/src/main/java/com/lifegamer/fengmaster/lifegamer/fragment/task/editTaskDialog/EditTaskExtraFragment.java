package com.lifegamer.fengmaster.lifegamer.fragment.task.editTaskDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseFragment;

/**
 * Created by qianzise on 2017/10/16.
 */

public class EditTaskExtraFragment extends BaseFragment{

    public EditTaskExtraFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_task_extra,container,false);
    }

    @Override
    public String getName() {
        return "额外";
    }
}
