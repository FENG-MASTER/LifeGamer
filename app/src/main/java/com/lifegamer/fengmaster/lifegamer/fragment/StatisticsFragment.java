package com.lifegamer.fengmaster.lifegamer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.fragment.base.BaseTabListFragment;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseTabListFragment {


    @Override
    public void onActionButtonClick() {
        Task task=new Task();
        task.setName("任务"+new Random().nextInt(50));
        task.setUrgency(51);
        task.setDifficulty(10);
        task.setFear(20);
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(3);
        task.setPreTasks(list);
        task.setCreateTime(new Date());
        task.setExpirationTime(new Date());
        Game.getInstance().getTaskManager().addTask(task);
    }
}
