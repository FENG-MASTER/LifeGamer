package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Item;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 物品管理器
 */

public interface IItemManager {

    /**
     * 添加新的物品
     * @param item 物品
     * @return 是否成功
     */
    boolean addItem(Item item);

    /**
     * 删除物品
     * @param item 物品名称
     * @return 是否成功
     */
    boolean removeItem(String item);

    /**
     * 失去物品
     * @param name 物品名称
     * @return 是否成功
     */
    boolean lostItem(String name);

    /**
     * 使用物品
     * @param item 物品名称
     * @return 是否成功
     */
    boolean useItem(String item);

    /**
     * 获取物品信息
     * @param item 物品名称
     * @return 物品
     */
    Item getItem(String item);

    /**
     * 赢得物品
     * @param item 物品名称
     * @return 是否成功
     */
    boolean gainItem(String item);

    /**
     * 赢得物品
     * @param item 物品ID
     * @return 是否成功
     */
    boolean gainItem(int item);

    /**
     * 获得所有物品列表
     * @return 物品列表
     */
    List<Item> getAllItem();

    /**
     * 获得所有 特定分类 物品列表
     * @param type 分类
     * @return 物品列表
     */
    List<Item> getAllItem(String type);

    /**
     * 获得所有 未获得的物品列表
     * @return 物品列表
     */
    List<Item> getAllNoGotItem();

    /**
     * 获得所有 特定分类 未获得的物品列表
     * @param type 分类
     * @return 物品列表
     */
    List<Item> getAllNoGotItem(String type);

    /**
     * 获得所有未获得的物品名称列表
     * @return 物品名称列表
     */
    List<String> getAllNoGotItemName();

    /**
     * 获得所有 特定分类 未获得的物品名称列表
     * @param type 分类
     * @return 物品名称列表
     */
    List<String> getAllNoGotItemName(String type);


    /**
     * 更新物品信息
     * @param item 物品
     * @return 是否成功
     */
    boolean updateItem(Item item);
}
