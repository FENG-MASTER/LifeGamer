package com.lifegamer.fengmaster.lifegamer.adapter.list.reward;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;

/**
 * 分类奖励适配器
 * Created by qianzise on 2017/10/22.
 */

public class TypeRewardFragmentAdapter extends BaseRewardFragmentAdapter {

    private String type;

    public TypeRewardFragmentAdapter(String type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return type;
    }


    @Override
    public void updateShowList() {
        showList= Game.getInstance().getRewardManager().getAllRewardItem(type);
    }
}
