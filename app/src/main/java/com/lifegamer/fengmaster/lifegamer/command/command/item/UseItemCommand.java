package com.lifegamer.fengmaster.lifegamer.command.command.item;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * 使用物品命令
 * Created by FengMaster on 19/01/19.
 */
public class UseItemCommand extends AbsNoCancelableCommand {
    private Item item;

    public UseItemCommand(Item item) {
        this.item = item;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getItemManager().useItem(item.getId());
    }

    @Override
    public String getName() {
        return "消耗"+item.getName();
    }
}
