package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.event.item.newItemEvent;
import com.lifegamer.fengmaster.lifegamer.event.item.DeleteItemEvent;
import com.lifegamer.fengmaster.lifegamer.event.item.UpdateItemEvent;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.Log;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;

import org.greenrobot.eventbus.EventBus;

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

    private Map<String,Item> items = new HashMap<>();

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
    public boolean addItem(Item item) {

        if (items.containsKey(item.getName())){
           return _addItemNum(item);
        }else {
            return _addItem(item);
        }
    }

    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.ADD,property = Log.PROPERTY.DEFAULT)
    private boolean _addItemNum(Item item){
        //已经存在相同物品,需要做的是增加物品数量
        Item mainItem=items.get(item.getName());
        //更新数量
        mainItem.addQuantity(item.getQuantity());
        //更新更新时间
        mainItem.setUpdateTime(new Date());

        //同步更新
        return updateItem(mainItem);
    }

    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.CREATE,property = Log.PROPERTY.DEFAULT)
    private boolean _addItem(Item item){
        long l = Game.insert(item);
        if (l != 0) {
            item.setId(l);
            items.put(item.getName(),item);
        }
        EventBus.getDefault().post(new newItemEvent(item));
        return l != 0;
    }

    /**
     * 删除物品 连带删除商店中的物品
     *
     * @param name 物品名称
     * @return 是否成功
     */
    @Override
    public boolean removeItem(String name) {
        Item item = Stream.of(items).filter(value -> value.getValue().getName().equals(name)).findFirst().get().getValue();
       return _removeItem(item);
    }

    /**
     *  删除物品 连带删除商店中的物品
     *
     * @param id 物品id
     * @return 是否成功
     */
    @Override
    public boolean removeItem(long id) {
        Item item = Stream.of(items).filter(value -> value.getValue().getId() == id).findFirst().get().getValue();
        return _removeItem(item);
    }

    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.DELETE,property = Log.PROPERTY.DEFAULT)
    private boolean _removeItem(Item item){
        if (item != null) {
            RewardItem rewardItem = Game.getInstance().getRewardManager().getRewardItemByItemId(item.getId());
            if (rewardItem!=null){
                Game.getInstance().getRewardManager().removeRewardItem(rewardItem.getId());
            }
            if (Game.delete(item)) {
                items.remove(item.getName());
                EventBus.getDefault().post(new DeleteItemEvent(item));
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
                item.reduceQuantity(1);
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
        Optional<Map.Entry<String, Item>> optional = Stream.of(items).filter(value -> value.getValue().getName().equals(itemName)).findFirst();
        if (optional.isPresent()){
            return optional.get().getValue();
        }else {
            return null;
        }
    }

    /**
     * 获得物品详细信息
     *
     * @param id 物品id
     * @return 物品
     */
    @Override
    public Item getItem(long id) {
        Optional<Map.Entry<String, Item>> first = Stream.of(items).filter(value -> value.getValue().getId() == id).findFirst();
        if (first.isPresent()){
            return first.get().getValue();
        }else {
            return null;
        }
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

    @Override
    public List<Item> getAllOwnItem() {
        return Stream.of(getAllItem()).filter(item -> item.getQuantity()!=0).collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllOwnItem(String type) {
        return Stream.of(getAllOwnItem()).filter(value -> value.getType().equals(type)).collect(Collectors.toList());
    }


    /**
     * 更新物品
     * @param item 物品
     * @return 是否成功
     */
    @Override
    public boolean updateItem(Item item) {
        return _updateItem(item);
    }

    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.EDIT,property = Log.PROPERTY.DEFAULT)
    private boolean _updateItem(Item item){
        EventBus.getDefault().post(new UpdateItemEvent(item));
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
            Item item = new Item();
            item.getFromCursor(cursor);
            items.put(item.getName(),item);
        }
        cursor.close();

    }


}
