package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 成就管理器接口
 */

public interface IAchievementManager {

    /**
     * 添加成就(只是添加,不获得)
     *
     * @param achievement 成就
     * @return 是否成功
     */
    boolean addAchievement(Achievement achievement);

    /**
     * 删除成就
     *
     * @param name 成就名称
     * @return 是否删除成功
     */
    boolean removeAchievement(String name);

    /**
     * 删除成就
     *
     * @param id 成就id
     */
    boolean removeAchievement(long id);

    /**
     * 更新成就
     * @param achievement 成就
     * @return 是否成功
     */
    boolean updateAchievement(Achievement achievement);

    /**
     * 获得成就
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievement}
     *
     * @param id 成就ID
     * @return 是否成功
     */
    boolean gainAchievement(long id);

    /**
     * 获得成就
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievement}
     *
     * @param name 成就名称
     * @return 是否成功
     */
    boolean gainAchievement(String name);


    /**
     * 获得成就
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievement}
     *
     * @param achievementReward 成就奖励
     * @return 是否成功
     */
    boolean gainAchievement(AchievementReward achievementReward);

    /**
     * 获取成就信息
     *
     * @param id 成就ID
     * @return 成就对象
     */
    Achievement getAchievement(long id);

    /**
     * 获取所有成就列表
     *
     * @return 成就列表
     */
    List<Achievement> getAllAchievement();


    /**
     * 获取 未获得 的成就列表
     *
     * @return 成就列表
     */
    List<Achievement> getAllNoGetAchievment();


    /**获取 所有已经 获得 的成就列表
     * @return 列表
     */
    List<Achievement> getAllGotAchievement();


    /**
     * 获取所有成就列表
     *
     * @param type  成就分类
     * @return 成就列表
     */
    List<Achievement> getAllAchievement(String type);


    /**
     * 获取 未获得 的成就列表
     *
     * @param type    成就分类
     * @return 成就列表
     */
    List<Achievement> getAllNoGetAchievment(String type);

    /**获取 所有已经 获得 的成就列表
     * @param type    成就分类
     * @return 列表
     */
    List<Achievement> getAllGotAchievement(String type);


    /**
     * 失去成就
     * <p>
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievement}
     *
     * @param id 成就id
     */
    boolean lostAchievement(long id);

    /**
     * 失去成就
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.LostAchievement}
     *
     * @param achievementReward 成就
     */
    boolean lostAchievement(AchievementReward achievementReward);


    /**
     * 获得成就所有分类
     * @return 分类列表
     */
    List<String> getAllType();


}
