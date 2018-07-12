package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 任务实体类
 */

public class Task extends BaseObservable implements Updateable, Insertable, Deleteable,ICopy<Task> {


    /**
     * 只能完成一次
     */
    public static final int REP_ONCE = 1;
    /**
     * 可重复完成的
     * <p>
     * 不存在时间间隔{@link Task#repeatInterval}无效
     * <p>
     * 过期时间无效{@link Task#expirationTime}无效
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

/***********************S基础信息S**************************/
    /**
     * 任务ID
     */
    private long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务描述
     */
    private String desc;

    /**
     * 任务图标
     */
    private String icon;
/***********************E基础信息E**************************/

/***********************S扩展信息S**************************/
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
     * 前置任务ID列表
     */
    private List<Integer> preTasks;
/***********************E扩展信息E**************************/

/***********************S奖励信息S**************************/

    /**
     * 任务完成后增加相应技能
     * <p>
     * key-技能ID val-增加的点数
     */
    private Map<Long, Integer> successSkills;
    /**
     * 任务完成后获得的物品列表
     */
    private List<RandomItemReward> successItems;
    /**
     * 任务完成后获得的成就列表
     */
    private List<AchievementReward> successAchievements;
    /**
     * 任务失败后减少相应技能
     * <p>
     * key-技能ID val-增加的点数
     */
    private Map<Long, Integer> failureSkills;
    /**
     * 任务失败后失去的物品列表
     * <p>
     */
    private List<RandomItemReward> failureItems;
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
/***********************E奖励信息E**************************/

/***********************S时间信息S**************************/

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
     * <p>
     * -1表示无限
     */
    private int repeatAvailableTime;
    /**
     * 任务到期时间
     */
    private Date expirationTime=new Date();
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
    private int completeTimes;
    /**
     * 任务失败次数
     */
    private int failureTimes;
    /**
     * 任务过期后是否自动失败
     */
    private boolean isAutoFail;
/***********************S时间信息S**************************/


    /**
     * 笔记ID
     */
    private List<Integer> notes;

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("desc", getDesc());
        cv.put("isAutoFail", isAutoFail());
        cv.put("icon", getIcon());
        cv.put("difficulty", getDifficulty());
        cv.put("urgency", getUrgency());
        cv.put("fear", getFear());
        cv.put("successSkills", FormatUtil.skillMap2Str(getSuccessSkills()));
        cv.put("successItems", FormatUtil.itemRewardList2Str(getSuccessItems()));
        cv.put("successAchievements", FormatUtil.achievementRewardList2Str(getSuccessAchievements()));
        cv.put("failureSkills", FormatUtil.skillMap2Str(getFailureSkills()));
        cv.put("failureItems", FormatUtil.itemRewardList2Str(getFailureItems()));
        cv.put("failureAchievements", FormatUtil.achievementRewardList2Str(getFailureAchievements()));
        cv.put("earnLP", getEarnLP());
        cv.put("lostLP", getLostLP());
        cv.put("repeatType", getRepeatType());
        cv.put("repeatInterval", getRepeatInterval());
        cv.put("repeatAvailableTime", getRepeatAvailableTime());

        if (expirationTime != null) {
            cv.put("expirationTime", SimpleDateFormat.getInstance().format(getExpirationTime()));
        } else {
            cv.put("expirationTime", "");
        }

        if (createTime != null) {
            cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        } else {
            cv.put("createTime", "");
        }

        if (updateTime != null) {
            cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));
        } else {
            cv.put("updateTime", "");
        }

        cv.put("completeTimes", getCompleteTimes());
        cv.put("failureTimes", getFailureTimes());
        cv.put("preTasks", FormatUtil.list2Str(getPreTasks()));
        cv.put("notes", FormatUtil.list2Str(getNotes()));

        return sqLiteDatabase.update(DBHelper.TABLE_TASK, cv, "_id = ?", new String[]{String.valueOf(getId())});

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    @Bindable
    public boolean isAutoFail() {
        return isAutoFail;
    }

    public void setAutoFail(boolean autoFail) {
        isAutoFail = autoFail;
        notifyPropertyChanged(BR.autoFail);
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }

    @Bindable
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
        notifyPropertyChanged(BR.difficulty);
    }

    @Bindable
    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
        notifyPropertyChanged(BR.urgency);
    }

    @Bindable
    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
        notifyPropertyChanged(BR.fear);
    }

    @Bindable
    public Map<Long, Integer> getSuccessSkills() {
        return successSkills;
    }

    public void setSuccessSkills(Map<Long, Integer> successSkills) {
        this.successSkills = successSkills;
        notifyPropertyChanged(BR.successSkills);
    }

    @Bindable
    public List<RandomItemReward> getSuccessItems() {
        return successItems;
    }

    public void setSuccessItems(List<RandomItemReward> successItems) {
        this.successItems = successItems;
        notifyPropertyChanged(BR.successItems);
    }

    @Bindable
    public List<AchievementReward> getSuccessAchievements() {
        return successAchievements;
    }

    @Bindable
    public Map<Long, Integer> getFailureSkills() {
        return failureSkills;
    }

    public void setFailureSkills(Map<Long, Integer> failureSkills) {
        this.failureSkills = failureSkills;
        notifyPropertyChanged(BR.failureSkills);
    }

    @Bindable
    public List<RandomItemReward> getFailureItems() {
        return failureItems;
    }

    public void setFailureItems(List<RandomItemReward> failureItems) {
        this.failureItems = failureItems;
        notifyPropertyChanged(BR.failureItems);
    }

    @Bindable
    public List<AchievementReward> getFailureAchievements() {
        return failureAchievements;
    }

    public void setFailureAchievements(List<AchievementReward> failureAchievements) {
        this.failureAchievements = failureAchievements;
        notifyPropertyChanged(BR.failureAchievements);
    }

    @Bindable
    public int getEarnLP() {
        return earnLP;
    }

    @Bindable
    public int getLostLP() {
        return lostLP;
    }


    public void setLostLP(int lostLP) {
        this.lostLP = lostLP;
        notifyPropertyChanged(BR.lostLP);
    }

    @Bindable
    public int getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
        notifyPropertyChanged(BR.repeatType);
    }

    @Bindable
    public int getRepeatInterval() {
        return repeatInterval;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
        notifyPropertyChanged(BR.repeatInterval);
    }

    @Bindable
    public int getRepeatAvailableTime() {
        return repeatAvailableTime;
    }

    public void setRepeatAvailableTime(int repeatAvailableTime) {
        this.repeatAvailableTime = repeatAvailableTime;
        notifyPropertyChanged(BR.repeatAvailableTime);
    }

    @Bindable
    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
        notifyPropertyChanged(BR.expirationTime);
    }

    @Bindable
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        notifyPropertyChanged(BR.createTime);
    }

    @Bindable
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        notifyPropertyChanged(BR.updateTime);
    }

    @Bindable
    public int getCompleteTimes() {
        return completeTimes;
    }

    public void setCompleteTimes(int completeTimes) {
        this.completeTimes = completeTimes;
        notifyPropertyChanged(BR.completeTimes);
    }

    @Bindable
    public int getFailureTimes() {
        return failureTimes;
    }

    public void setFailureTimes(int failureTimes) {
        this.failureTimes = failureTimes;
        notifyPropertyChanged(BR.failureTimes);
    }

    @Bindable
    public List<Integer> getPreTasks() {
        return preTasks;
    }

    @Bindable
    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setPreTasks(List<Integer> preTasks) {
        this.preTasks = preTasks;
        notifyPropertyChanged(BR.preTasks);
    }

    public void setEarnLP(int earnLP) {
        this.earnLP = earnLP;
        notifyPropertyChanged(BR.earnLP);
    }

    public void setSuccessAchievements(List<AchievementReward> successAchievements) {
        this.successAchievements = successAchievements;
        notifyPropertyChanged(BR.successAchievements);
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        if (getId() != 0) {
            cv.put("_id", getId());
        }
        cv.put("name", getName());
        cv.put("desc", getDesc());
        cv.put("isAutoFail", isAutoFail());
        cv.put("icon", getIcon());
        cv.put("difficulty", getDifficulty());
        cv.put("urgency", getUrgency());
        cv.put("fear", getFear());
        cv.put("successSkills", FormatUtil.skillMap2Str(getSuccessSkills()));
        cv.put("successItems", FormatUtil.itemRewardList2Str(getSuccessItems()));
        cv.put("successAchievements", FormatUtil.achievementRewardList2Str(getSuccessAchievements()));
        cv.put("failureSkills", FormatUtil.skillMap2Str(getFailureSkills()));
        cv.put("failureItems", FormatUtil.itemRewardList2Str(getFailureItems()));
        cv.put("failureAchievements", FormatUtil.achievementRewardList2Str(getFailureAchievements()));
        cv.put("earnLP", getEarnLP());
        cv.put("lostLP", getLostLP());
        cv.put("repeatType", getRepeatType());
        cv.put("repeatInterval", getRepeatInterval());
        cv.put("repeatAvailableTime", getRepeatAvailableTime());
        if (expirationTime != null) {
            cv.put("expirationTime", SimpleDateFormat.getInstance().format(getExpirationTime()));
        } else {
            cv.put("expirationTime", "");
        }

        if (createTime != null) {
            cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        } else {
            cv.put("createTime", "");
        }

        if (updateTime != null) {
            cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));
        } else {
            cv.put("updateTime", "");
        }

        cv.put("completeTimes", getCompleteTimes());
        cv.put("failureTimes", getFailureTimes());
        cv.put("preTasks", FormatUtil.list2Str(getPreTasks()));
        cv.put("notes", FormatUtil.list2Str(getNotes()));

        return sqLiteDatabase.insert(DBHelper.TABLE_TASK, null, cv);
    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_TASK, "_id=?", new String[]{String.valueOf(getId())});
    }

    @Override
    public void copyFrom(Task task) {
        this.setId(task.getId());
        this.setName(task.getName());
        this.setDesc(task.getDesc());
        this.setFear(task.getFear());
        this.setDifficulty(task.getDifficulty());
        this.setUrgency(task.getUrgency());
        this.setEarnLP(task.getEarnLP());
        this.setLostLP(task.getLostLP());
        this.setAutoFail(task.isAutoFail());
        this.setCompleteTimes(task.getCompleteTimes());
        this.setFailureTimes(task.getFailureTimes());
        this.setSuccessAchievements(task.getSuccessAchievements());
        this.setSuccessItems(task.getSuccessItems());
        this.setSuccessSkills(task.getSuccessSkills());
        this.setFailureAchievements(task.getFailureAchievements());
        this.setFailureItems(task.getFailureItems());
        this.setFailureSkills(task.getFailureSkills());
        this.setCreateTime(task.getCreateTime());
        this.setUpdateTime(task.getUpdateTime());
        this.setNotes(task.getNotes());
        this.setRepeatType(task.getRepeatType());
        this.setRepeatAvailableTime(task.getRepeatAvailableTime());
        this.setRepeatInterval(task.getRepeatInterval());
        this.setIcon(task.getIcon());
        this.setPreTasks(task.getPreTasks());


    }
}
