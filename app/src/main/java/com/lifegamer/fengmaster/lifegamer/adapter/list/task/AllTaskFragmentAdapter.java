package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * Created by qianzise on 2017/10/15.
 */

public class AllTaskFragmentAdapter extends BaseTaskFragmentAdapter {
    @Override
    public String getName() {
        return "所有任务";
    }

    @Override
    public void updateTaskList() {
        showTaskList= Game.getInstance().getTaskManager().getAllTask();
    }
}
