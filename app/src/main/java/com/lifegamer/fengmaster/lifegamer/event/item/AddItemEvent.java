package com.lifegamer.fengmaster.lifegamer.event.item;

import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * 新增物品事件
 */
public class AddItemEvent {

    private Item item;

    public AddItemEvent(Item item) {
        this.item = item;
    }
}
