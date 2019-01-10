package com.lifegamer.fengmaster.lifegamer.event.item;

import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * 有物品信息更新事件
 * Created by FengMaster on 19/01/10.
 */
public class UpdateItemEvent {

    private Item item;

    public UpdateItemEvent(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
