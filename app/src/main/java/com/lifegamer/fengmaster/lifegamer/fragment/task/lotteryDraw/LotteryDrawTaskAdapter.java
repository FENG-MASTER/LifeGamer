package com.lifegamer.fengmaster.lifegamer.fragment.task.lotteryDraw;

import com.lifegamer.fengmaster.lifegamer.adapter.list.task.BaseTaskFragmentAdapter;
import com.lifegamer.fengmaster.lifegamer.model.Task;

import java.util.ArrayList;

/**
 * Created by FengMaster on 19/02/21.
 */
public class LotteryDrawTaskAdapter extends BaseTaskFragmentAdapter {


    public LotteryDrawTaskAdapter() {
        showList=new ArrayList<>();
    }

    @Override
    public void updateShowList() {
    }

    @Override
    public String getName() {
        return "";
    }

    public void addTask(Task task){
        showList.add(task);
        notifyDataSetChanged();
    }
}
