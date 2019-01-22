package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.model.Task;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 每日任务适配器
 */

public class DailyTaskFragmentAdapter extends BaseTaskFragmentAdapter {


    @Override
    public String getName() {
        return "每日";
    }

    @Override
    public void updateShowList() {
        showList= Stream.of(Game.getInstance().getTaskManager().getAllTask()).filter(value -> value.getRepeatType()==Task.REP_DAILY).toList();
    }
}
