package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.lifegamer.fengmaster.lifegamer.Game;

/**
 * 今日任务fragment
 * Created by FengMaster on 18/07/11.
 */
public class TodayTaskFragmentAdapter extends BaseTaskFragmentAdapter {
    @Override
    public void updateShowList() {
        showList= Game.getInstance().getTaskManager().getTodayUnFinishTask();
    }

    @Override
    public String getName() {
        return "今日任务";
    }
}
