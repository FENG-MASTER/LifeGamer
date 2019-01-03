package com.lifegamer.fengmaster.lifegamer.command.command.item;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
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
    public boolean execute() {
        return Game.getInstance().getItemManager().addItem(item);
    }

    @Override
    public void undo() {
        Game.getInstance().getItemManager().removeItem(item.getName());
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.item_add)+item.getName();
    }


}
