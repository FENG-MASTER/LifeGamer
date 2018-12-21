package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

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
        return App.getContext().getString(R.string.task_today);
    }
}
