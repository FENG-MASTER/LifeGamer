package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Item;
import com.lifegamer.fengmaster.lifegamer.model.RewardItem;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;

import java.util.List;

/**
 * Created by qianzise on 2017/10/21.
 */

public interface IRewardManager {

    /**
     * 根据物品ID获取 奖励 详情
     *
     * @param id 物品id
     * @return 详情
     */
    RewardItem getRewardItemByItemId(long id);


    /**
     * 获取 奖励 详情
     *
     * @param id 奖励id
     * @return 详情
     */
    RewardItem getRewardItem(long id);

    /**
     * 获取 奖励 详情
     *
     * @param name 奖励名
     * @return 详情
     */
    RewardItem getRewardItem(String name);


    /**
     * 添加新的奖励物品
     *
     * @param rewardItem 奖励物品
     * @return 是否成功
     */
    boolean addRewardItem(RewardItem rewardItem);


    /**
     * 更新奖励物品
     *
     * @param rewardItem 奖励物品
     * @return 是否成功
     */
    boolean updateRewardItem(RewardItem rewardItem);


    /**
     * 删除奖励物品
     *
     * @param id 奖励物品id
     * @return 是否成功
     */
    boolean removeRewardItem(long id);


    /**
     * 赢得奖励
     *
     * @param rewardItem 奖励
     * @param num  数量
     * @param probability 千分比例
     * @return 是否成功
     */
    boolean gainRewardItem(String rewardItem, int num, int probability);


    /**
     * 赢得奖励
     *
     *
     * @param rewardItemID id
     * @param num  数量
     * @param probability 千分比例
     * @return 是否成功
     */
    boolean gainRewardItem(int rewardItemID, int num, int probability);


    /**
     * 赢得随机奖励
     * @param randomItemReward 随机奖励
     * @return 是否获取
     */
    boolean gainRewardItem(RandomItemReward randomItemReward);


    /**
     * 获得奖励
     * @param rewardItem 奖励对象
     * @return 是否成功
     */
    boolean gainRewardItem(RewardItem rewardItem,int num);


    /**
     * 获得奖励
     * @param rewardItemID 奖励id
     * @param num
     * @return 是否成功
     */
    boolean gainRewardItem(int rewardItemID,int num);


    /**
     * 取消获得奖励
     *
     * @param rewardItem 要取消的奖励
     * @return 是否成功
     */
    boolean lostRewardItem(RewardItem rewardItem,int num);

    /**
     * 取消获得奖励
     *
     * @param rewardItem 要取消的奖励
     * @return 是否成功
     */
    boolean lostRewardItem(int rewardItemID,int num);

    /**
     * 购买奖励
     *
     * @param rewardItemID 奖励ID
     * @return 是否购买成功
     */
    boolean buyRewardItem(int rewardItemID,int num);

    /**
     * 购买奖励
     *
     * @param rewardItem 奖励名
     * @return 是否购买成功
     */
    boolean buyRewardItem(String rewardItem,int num);

    /**
     * 取消购买奖励,即退货
     *
     * @param rewardItem 要取消的奖励
     * @return 是否成功
     */
    boolean returnRewardItem(RewardItem rewardItem);

    /**
     * 获得所有  奖励物品
     *
     * @return 奖励物品列表
     */
    List<RewardItem> getAllRewardItem();

    /**
     * 获得所有 特定分类 奖励物品
     *
     * @param type 分类
     * @return 奖励物品列表
     */
    List<RewardItem> getAllRewardItem(String type);

    /**
     * 获得所有 未获得的奖励物品列表
     *
     * @return 奖励物品列表
     */
    List<RewardItem> getAllAvailableRewardItem();

    /**
     * 获得所有 特定分类 未获得的奖励物品列表
     *
     * @param type 分类
     * @return 奖励物品列表
     */
    List<RewardItem> getAllAvailableRewardItem(String type);

    /**
     * 获得所有未获得的奖励物品名称列表
     *
     * @return 奖励物品名称列表
     */
    List<String> getAllAvailableRewardItemName();

    /**
     * 获得所有 特定分类 未获得的奖励物品名称列表
     *
     * @param type 分类
     * @return 奖励物品名称列表
     */
    List<String> getAllAvailableRewardItemName(String type);


}
