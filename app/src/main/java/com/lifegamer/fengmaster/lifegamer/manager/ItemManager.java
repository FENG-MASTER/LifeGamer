package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 本地物品管理器
 */

public class ItemManager implements IItemManager{

    private List<Item> items=new ArrayList<>();

    private DBHelper helper=DBHelper.getInstance();


    public ItemManager() {
        loadItemsFromSQL();
    }




    @Override
    public boolean gainItem(Item item) {
        long l = Game.insert(item);
        if (l!=0){
            item.setId(l);
            items.add(item);
        }

        return l!=0;
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
    public Item getItem(long id) {
        return null;
    }

    @Override
    public List<Item> getAllItem() {
        return items;
    }


    @Override
    public boolean updateItem(Item item) {
        return Game.update(item);
    }

    /**
     * 从数据库更新所有数据
     */
    private void loadItemsFromSQL(){
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_ITEM, null, null, null, null, null, null);
        while (cursor.moveToNext()){
            Item item = getItemFromCursor(cursor);
            items.add(item);
        }

    }


    private Item getItemFromCursor(Cursor cursor){
        Item item=new Item();
        item.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        item.setName(cursor.getString(cursor.getColumnIndex("name")));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
        item.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        item.setExpendable(cursor.getInt(cursor.getColumnIndex("expendable"))==1);
        item.setType(cursor.getString(cursor.getColumnIndex("type")));
        item.setIcon(cursor.getString(cursor.getColumnIndex("icon")));

        item.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));


        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime != null && !createTime.equals("")) {
            try {
                item.setCreateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("createTime"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));
        if (updateTime != null && updateTime.equals("")) {
            try {
                item.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return item;

    }
}
