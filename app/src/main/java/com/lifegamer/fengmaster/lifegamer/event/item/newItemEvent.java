package com.lifegamer.fengmaster.lifegamer.event.item;

import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * 新增物品事件
 */
public class newItemEvent {

    private Item item;

    public newItemEvent(Item item) {
        this.item = item;
    }
}
