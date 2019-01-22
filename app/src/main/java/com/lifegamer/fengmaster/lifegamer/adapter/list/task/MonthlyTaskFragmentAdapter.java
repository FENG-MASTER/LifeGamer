package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 每月任务适配器
 */

public class MonthlyTaskFragmentAdapter extends BaseTaskFragmentAdapter {


    @Override
    public String getName() {
        return "每月";
    }

    @Override
    public void updateShowList() {
        showList= Stream.of(Game.getInstance().getTaskManager().getAllTask()).filter(value -> value.getRepeatType()==Task.REP_MONTHLY).toList();
    }
}
