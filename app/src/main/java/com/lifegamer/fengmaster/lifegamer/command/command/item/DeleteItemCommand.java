package com.lifegamer.fengmaster.lifegamer.command.command.item;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Item;

public class DeleteItemCommand extends AbsNoCancelableCommand {
    private Item item;

    public DeleteItemCommand(Item item) {
        this.item = item;
    }

    @Override
    public boolean execute() {
        return Game.getInstance().getItemManager().removeItem(item.getId());
    }

    @Override
    public String getName() {
        return "删除" +item.getName();
    }
}
