package com.lifegamer.fengmaster.lifegamer.command.command.item;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.command.command.AbsNoCancelableCommand;
import com.lifegamer.fengmaster.lifegamer.model.Item;

/**
 * Created by qianzise on 2017/10/22.
 *
 * 更新物品命令
 */

public class UpdateItemCommand extends AbsNoCancelableCommand {

    private Item item;


    public UpdateItemCommand(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        Game.getInstance().getItemManager().updateItem(item);
    }

    @Override
    public String getName() {
        return App.getContext().getString(R.string.item_update)+item.getName();
    }
}
