package com.lifegamer.fengmaster.lifegamer.adapter.list.log;

import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.model.Log;


/**
 * 所有日志足迹列表
 * Created by FengMaster on 19/01/28.
 */
public class AllLogAdapter extends BaseLogAdapter{
    @Override
    public void updateShowList() {
        showList= Stream.of(Game.getInstance().getLogManager().getAllLog()).
                filterNot(value -> value.getType().equals(Log.TYPE.TRIGGER)).
                sorted((o1, o2) -> Long.compare(o2.getId(),o1.getId())).toList();
    }


    @Override
    public String getName() {
        return "足迹";
    }
}
