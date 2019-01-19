package com.lifegamer.fengmaster.lifegamer.event.item;

import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * 仓库物品被删除 事件
 */
public class DeleteItemEvent {

    private Item item;

    public DeleteItemEvent(Item item) {
        this.item = item;
    }
}
