package com.lifegamer.fengmaster.lifegamer.adapter.list.item;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 分类物品 适配器
 */

public class TypeItemFragmentAdapter extends BaseItemFragmentAdapter {

    private String type;

    public TypeItemFragmentAdapter(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type;
    }

    @Override
    public void updateShowList() {
        showList= Game.getInstance().getItemManager().getAllItem(type);
    }
}
