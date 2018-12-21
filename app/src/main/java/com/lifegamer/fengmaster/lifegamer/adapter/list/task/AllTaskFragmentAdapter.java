package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 所有任务适配器
 */

public class AllTaskFragmentAdapter extends BaseTaskFragmentAdapter {


    @Override
    public String getName() {
        return App.getContext().getString(R.string.task_all);
    }

    @Override
    public void updateShowList() {
        showList= Game.getInstance().getTaskManager().getAllTask();
    }
}
