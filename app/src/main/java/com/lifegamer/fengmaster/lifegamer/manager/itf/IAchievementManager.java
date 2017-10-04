package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Achievement;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 成就管理器接口
 */

public interface IAchievementManager {

    /**
     * 添加成就(只是添加,不获得)
     * @param achievement 成就
     */
    void addAchievement(Achievement achievement);

    /**
     * 删除成就
     * @param name 成就名称
     */
    void removeAchievement(String name);

    /**
     * 删除成就
     * @param id 成就id
     */
    void removeAchievement(int id);

    /**
     * 获得成就
     * @param id 成就ID
     */
    void gainAchievement(int id);

    /**
     * 获得成就
     * {@link com.lifegamer.fengmaster.lifegamer.event.achievement.GotAchievement}
     * @param name 成就名称
     */
    void gainAchievement(String name);

    /**
     * 获取成就信息
     * @param id 成就ID
     * @return 成就对象
     */
    Achievement getAchievement(int id);

    /**
     * 获取所有成就列表
     * @return 成就列表
     */
    List<Achievement> getAllAchievement();


    /**
     * 获取 未获得 的成就列表
     * @return 成就列表
     */
    List<Achievement> getNoGetAchievment();

    /**
     * 失去成就
     * @param id 成就id
     */
    void lostAchievement(int id);



}
