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

public class EditTaskRewardFragment extends BaseFragment {

    public EditTaskRewardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_edit_task_reward,container,false);
    }

    @Override
    public String getName() {
        return "奖励";
    }
}
