package com.lifegamer.fengmaster.lifegamer.fragment.task;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.adapter.list.task.AllTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;


public class TaskFragment extends BaseTabListFragment {


    @Override
    public void onActionButtonClick() {
        addAdapter(new AllTaskFragmentAdapter());
    }
}
