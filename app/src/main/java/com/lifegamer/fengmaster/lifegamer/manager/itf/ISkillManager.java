package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 技能管理器
 */

public interface ISkillManager {

    /**
     * 增加技能点
     * @param skillName 技能名字
     * @param point 增加点数
     */
    void addSkillPoint(String skillName,int point);

    /**
     * 新增技能
     * @param skill 技能
     * @return 是否成功
     */
    boolean addSkill(Skill skill);

    /**
     * 更新技能
     * @param skill 技能
     * @return 是否成功
     */
    boolean updateSkill(Skill skill);

    /**
     * 根据名称返回技能
     * @param name 技能名称
     * @return 技能
     */
    Skill getSkill(String name);

    /**
     * 删除技能
     * @param name 技能名称
     */
    void removeSkill(String name);

    /**
     * 获取 所有技能名称
     * @return 名称列表
     */
    List<String> getAllSkillName();

    /**
     * 获取 所有技能
     * @return 技能列表
     */
    List<Skill> getAllSkill();

    /**
     * 获取特定分类的所有技能名称
     * @param type 技能分类
     * @return 列表
     */
    List<String> getAllSkillName(String type);

    /**
     * 获取特定分类的所有技能列表
     * @param type 技能分类
     * @return 列表
     */
    List<Skill> getAllSkill(String type);
}
