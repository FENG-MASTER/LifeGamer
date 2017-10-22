package com.lifegamer.fengmaster.lifegamer.command.command.item;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 新增物品命令
 */

public class AddItemCommand extends AbsCancelableCommand{

    private Item item;

    public AddItemCommand(Item item) {
        this.item = item;
    }


    @Override
    public void execute() {
        Game.getInstance().getItemManager().gainItem(item);
    }

    @Override
    public void undo() {
        Game.getInstance().getItemManager().lostItem(item.getName());
    }

    @Override
    public String getName() {
        return "新增物品"+item.getName();
    }

    @Override
    public String getUndoActionName() {
        return "撤销";
    }
}
