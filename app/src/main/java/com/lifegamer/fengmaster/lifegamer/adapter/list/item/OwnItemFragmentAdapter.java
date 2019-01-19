package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 所有已经获得的物品 适配器
 */

public class OwnItemFragmentAdapter extends BaseItemFragmentAdapter {

    @Override
    public String getName() {
        return "已获得";
    }

    @Override
    public void updateShowList() {
        showList= Stream.of(Game.getInstance().getItemManager().getAllItem()).filter(value -> value.getQuantity()==-1||value.getQuantity()>0).toList();

    }
}
