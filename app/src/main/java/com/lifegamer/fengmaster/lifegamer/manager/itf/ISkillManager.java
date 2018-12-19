package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Skill;

import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 能力管理器
 */

public interface ISkillManager {

    /**
     * 增加能力点
     * @param skillName 能力名字
     * @param xp 增加点数
     * @return 是否成功
     */
    boolean addSkillXp(String skillName, int xp);

    /**
     * 新增能力
     * @param skill 能力
     * @return 是否成功
     */
    boolean addSkill(Skill skill);

    /**
     * 更新能力
     * @param skill 能力
     * @return 是否成功
     */
    boolean updateSkill(Skill skill);

    /**
     * 根据名称返回能力
     * @param name 能力名称
     * @return 能力
     */
    Skill getSkill(String name);


    /**
     * 根据ID获得能力对象
     * @param id 能力id
     * @return 能力对象
     */
    Skill getSkill(long id);

    /**
     * 删除能力
     * @param name 能力名称
     */
    boolean removeSkill(String name);

    /**
     * 获取 所有能力名称
     * @return 名称列表
     */
    List<String> getAllSkillName();

    /**
     * 获取 所有能力
     * @return 能力列表
     */
    List<Skill> getAllSkill();

    /**
     * 获取特定分类的所有能力名称
     * @param type 能力分类
     * @return 列表
     */
    List<String> getAllSkillName(String type);

    /**
     * 获取特定分类的所有能力列表
     * @param type 能力分类
     * @return 列表
     */
    List<Skill> getAllSkill(String type);


    /**
     * 获取所有能力分类
     * @return 分类列表
     */
    List<String> getAllSkillType();
}
