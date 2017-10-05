package com.lifegamer.fengmaster.lifegamer.model;

import com.lifegamer.fengmaster.lifegamer.manager.AchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.ItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.SkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.TaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.WealthManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.INoteManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IWealthManager;

/**
 * Created by qianzise on 2017/10/4.
 */

public class Hero {
    private static Hero instance = new Hero();

    /**
     * 物品管理器
     */
    private IItemManager itemManager;
    /**
     * 成就管理器
     */
    private IAchievementManager achievementManager;
    /**
     * 技能管理器
     */
    private ISkillManager skillManager;
    /**
     * 财富管理器
     */
    private IWealthManager wealthManager;
    /**
     * 任务管理器
     */
    private ITaskManager taskManager;

    /**
     * 笔记管理器
     */
    private INoteManager noteManager;

    private String name;
    private int id = 1;
    private String title;
    private String introduction;
    private String avatarUrl;

    private int level;
    private int xp;
    private int upGradeXP;

    private Hero() {

    }

    public static Hero getInstance() {
        return instance;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void setUpGradeXP(int upGradeXP) {
        this.upGradeXP = upGradeXP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public IItemManager getItemManager() {
        return itemManager;
    }

    public IAchievementManager getAchievementManager() {
        return achievementManager;
    }

    public ISkillManager getSkillManager() {
        return skillManager;
    }

    public IWealthManager getWealthManager() {
        return wealthManager;
    }

    public ITaskManager getTaskManager() {
        return taskManager;
    }

    public INoteManager getNoteManager() {
        return noteManager;
    }
}
