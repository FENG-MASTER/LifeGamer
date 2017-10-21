package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 */

public class ItemManager implements IItemManager{

    private List<Item> items=new ArrayList<>();


    public ItemManager() {
        Item item1=new Item();
        item1.setName("物品1");
        item1.setQuantity(10);

        Item item2=new Item();
        item2.setName("物品2");
        item2.setQuantity(10);

        items.add(item1);
        items.add(item2);
    }




    @Override
    public boolean gainItem(Item item) {
        return false;
    }

    @Override
    public boolean lostItem(String name) {
        return false;
    }

    @Override
    public boolean useItem(String item) {
        return false;
    }

    @Override
    public Item getItem(String item) {
        return null;
    }

    @Override
    public List<Item> getAllItem() {
        return items;
    }


    @Override
    public boolean updateItem(Item item) {
        return false;
    }
}
