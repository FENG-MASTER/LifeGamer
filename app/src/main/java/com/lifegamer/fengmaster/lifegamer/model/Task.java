package com.lifegamer.fengmaster.lifegamer.model;

import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.ItemReward;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 任务实体类
 */

public class Task {


    /**
     * 只能完成一次
     */
    public static final int REP_ONCE = 1;
    /**
     * 可重复完成的
     */
    public static final int REP_CONTINUOUS = 2;
    /**
     * 每X小时重复
     */
    public static final int REP_HOURLY = 3;
    /**
     * 每X天重复
     */
    public static final int REP_DAILY = 4;
    /**
     * 每X星期重复
     */
    public static final int REP_WEEKLY = 5;
    /**
     * 每X月重复
     */
    public static final int REP_MONTHLY = 6;
    /**
     * 每X年重复
     */
    public static final int REP_YEARLY = 7;
    /**
     * 没
     */
    public static final int REP_DAIIY = 8;


    /**
     * 任务ID
     */
    private int id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String desc;

    /**
     * 任务过期后是否自动失败
     */
    private boolean isAutoFail;
    /**
     * 任务图标
     */
    private String icon;
    /**
     * 困难程度
     */
    private int difficulty;
    /**
     * 紧急程度
     */
    private int urgency;
    /**
     * 害怕程度
     */
    private int fear;
    /**
     * 任务完成后增加相应技能
     * <p>
     * key-技能名称 val-增加的点数
     */
    private Map<String, Integer> successSkills;
    /**
     * 任务完成后获得的物品列表
     * <p>
     * key-物品名称 val-物品个数
     */
    private List<ItemReward> successItems;
    /**
     * 任务完成后获得的成就列表
     */
    private List<AchievementReward> successAchievements;
    /**
     * 任务失败后减少相应技能
     * <p>
     * key-技能名称 val-增加的点数
     */
    private Map<String, Integer> failureSkills;
    /**
     * 任务失败后失去的物品列表
     * <p>
     * key-物品名称 val-物品个数
     */
    private List<ItemReward> failureItems;
    /**
     * 任务失败后失去的成就列表
     */
    private List<AchievementReward> failureAchievements;
    /**
     * 任务完成后奖励的LP
     */
    private int earnLP;

    /**
     * 任务失败后扣除的LP
     */
    private int lostLP;
    /**
     * 任务重复类型
     */
    private int repeatType;
    /**
     * 重复的间隔时间(根据类型不同表示的单位不同)
     */
    private int repeatInterval;
    /**
     * 可重复次数
     */
    private int repeatAvailableTime;
    /**
     * 任务到期时间
     */
    private Date expirationTime;
    /**
     * 任务创建时间
     */
    private Date createTime;
    /**
     * 任务更新时间
     */
    private Date updateTime;
    /**
     * 完成任务的次数
     */
    private int completeTime;
    /**
     * 任务失败次数
     */
    private int failureTime;
    /**
     * 前置任务ID列表
     */
    private List<Integer> preTasks;

    public boolean isAutoFail() {
        return isAutoFail;
    }

    public void setAutoFail(boolean autoFail) {
        isAutoFail = autoFail;
    }

    public Map<String, Integer> getFailureSkills() {
        return failureSkills;
    }

    public void setFailureSkills(Map<String, Integer> failureSkills) {
        this.failureSkills = failureSkills;
    }

    public List<ItemReward> getFailureItems() {
        return failureItems;
    }

    public void setFailureItems(List<ItemReward> failureItems) {
        this.failureItems = failureItems;
    }

    public List<AchievementReward> getFailureAchievements() {
        return failureAchievements;
    }

    public void setFailureAchievements(List<AchievementReward> failureAchievements) {
        this.failureAchievements = failureAchievements;
    }

    public int getLostLP() {
        return lostLP;
    }

    public void setLostLP(int lostLP) {
        this.lostLP = lostLP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public Map<String, Integer> getSuccessSkills() {
        return successSkills;
    }

    public void setSuccessSkills(Map<String, Integer> successSkills) {
        this.successSkills = successSkills;
    }

    public List<ItemReward> getSuccessItems() {
        return successItems;
    }

    public void setSuccessItems(List<ItemReward> successItems) {
        this.successItems = successItems;
    }

    public List<AchievementReward> getSuccessAchievements() {
        return successAchievements;
    }

    public void setSuccessAchievements(List<AchievementReward> successAchievements) {
        this.successAchievements = successAchievements;
    }

    public int getEarnLP() {
        return earnLP;
    }

    public void setEarnLP(int earnLP) {
        this.earnLP = earnLP;
    }

    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
    }

    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    public int getRepeatAvailableTime() {
        return repeatAvailableTime;
    }

    public void setRepeatAvailableTime(int repeatAvailableTime) {
        this.repeatAvailableTime = repeatAvailableTime;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(int completeTime) {
        this.completeTime = completeTime;
    }

    public int getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(int failureTime) {
        this.failureTime = failureTime;
    }

    public List<Integer> getPreTasks() {
        return preTasks;
    }

    public void setPreTasks(List<Integer> preTasks) {
        this.preTasks = preTasks;
    }
}
