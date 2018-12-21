package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 所有物品 适配器
 */

public class AllItemFragmentAdapter extends BaseItemFragmentAdapter {
    @Override
    public void updateItemsList() {
        showItemList= Game.getInstance().getItemManager().getAllItem();
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.item_all);
    }
}
