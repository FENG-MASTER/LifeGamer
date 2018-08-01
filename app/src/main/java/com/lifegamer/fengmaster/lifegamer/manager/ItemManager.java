package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.Skill;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 本地物品管理器
 */

public class ItemManager implements IItemManager {

    private Map<Integer,Item> items = new HashMap<>();

    private DBHelper helper = DBHelper.getInstance();


    public ItemManager() {
        loadItemsFromSQL();
    }


    /**
     * 赢得物品
     *
     * @param item 物品
     * @return 是否成功
     */
    @Override
    public boolean gainItem(Item item) {

        if (items.containsKey((int)item.getId())){
            //已经存在相同物品,需要做的是增加物品数量
            Item mainItem=items.get((int) item.getId());
            //更新数量
            mainItem.setQuantity(mainItem.getQuantity()+item.getQuantity());
            //更新更新时间
            mainItem.setUpdateTime(new Date());

            //同步更新
            return updateItem(mainItem);

        }else {

            long l = Game.insert(item);
            if (l != 0) {
                item.setId(l);
                items.put((int) item.getId(),item);
            }

            return l != 0;

        }


    }

    /**
     * 失去物品
     *
     * @param name 物品名称
     * @return 是否成功
     */
    @Override
    public boolean lostItem(String name) {
        Item item = Stream.of(items).filter(value -> value.getValue().getName().equals(name)).findFirst().get().getValue();
        if (item != null) {
            if (Game.delete(item)) {
                items.remove(item);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 失去物品
     *
     * @param id 物品id
     * @return 是否成功
     */
    @Override
    public boolean lostItem(long id) {
        Item item = Stream.of(items).filter(value -> value.getValue().getId() == id).findFirst().get().getValue();
        if (item != null) {
            if (Game.delete(item)) {
                items.remove(item);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 使用物品
     *
     * @param itemName 物品名称
     * @return 是否成功
     */
    @Override
    public boolean useItem(String itemName) {
        Item item = getItem(itemName);
        return useItem(item);
    }

    /**
     * 使用物品
     *
     * @param id 物品id
     * @return 是否成功
     */
    @Override
    public boolean useItem(long id) {
        Item item = getItem(id);
        return useItem(item);
    }

    /**
     * 使用物品
     *
     * @param item 物品
     * @return 是否成功
     */
    private boolean useItem(Item item) {
        if (item != null) {
            if (!item.isExpendable()) {
                //非消耗品
                return true;
            }
            //消耗品
            if (item.getQuantity() > 0) {
                //还有可用数量
                item.setQuantity(item.getQuantity() - 1);
                return updateItem(item);
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * 获得物品详细信息
     *
     * @param itemName 物品名
     * @return 物品
     */
    @Override
    public Item getItem(String itemName) {
        return Stream.of(items).filter(value -> value.getValue().getName().equals(itemName)).findFirst().get().getValue();
    }

    /**
     * 获得物品详细信息
     *
     * @param id 物品id
     * @return 物品
     */
    @Override
    public Item getItem(long id) {
        return Stream.of(items).filter(value -> value.getValue().getId() == id).findFirst().get().getValue();

    }

    /**
     * 获得所有物品列表
     * @return 物品列表
     */
    @Override
    public List<Item> getAllItem() {
        return new ArrayList<>(items.values());
    }

    /**
     * 获得所有 特定分类 的物品列表
     * @param type 分类
     * @return 列表
     */
    @Override
    public List<Item> getAllItem(String type) {
        return Stream.of(items).filter(value -> value.getValue().getType().equals(type)).map(Map.Entry::getValue).collect(Collectors.toList());
    }

    /**
     * 获得所有分类
     * @return 分类列表
     */
    @Override
    public List<String> getAllType() {
        return Stream.of(items).map(integerItemEntry -> integerItemEntry.getValue().getType()).distinct().collect(Collectors.toList());

    }


    /**
     * 更新物品
     * @param item 物品
     * @return 是否成功
     */
    @Override
    public boolean updateItem(Item item) {
        if (items.containsValue(item)){
            //缓存有
            return Game.update(item);
        }else {
            Item it = getItem(item.getId());
            if (it!=null){
                //发现有相同id
                //更新原来数据
                it.copyFrom(item);
                return Game.update(it);
            }else {
                return false;
            }

        }
    }

    /**
     * 从数据库更新所有数据
     */
    private void loadItemsFromSQL() {
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_ITEM, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Item item = getItemFromCursor(cursor);
            items.put((int) item.getId(),item);
        }
        cursor.close();

    }


    private Item getItemFromCursor(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        item.setName(cursor.getString(cursor.getColumnIndex("name")));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
        item.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        item.setExpendable(cursor.getInt(cursor.getColumnIndex("expendable")) == 1);
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
