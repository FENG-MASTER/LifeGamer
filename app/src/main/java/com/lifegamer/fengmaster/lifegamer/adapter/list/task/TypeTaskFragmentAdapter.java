package com.lifegamer.fengmaster.lifegamer.adapter.list.task;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/15.
 *
 * 任务分类适配器
 */

public class TypeTaskFragmentAdapter extends BaseTaskFragmentAdapter {

    private String type;

    public TypeTaskFragmentAdapter(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public void updateShowList() {
        showList= Game.getInstance().getTaskManager().getTaskByType(type);
    }
}
