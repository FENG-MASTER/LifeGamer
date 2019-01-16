package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Getable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.manager.TriggerManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITriggerManager;
import com.lifegamer.fengmaster.lifegamer.model.base.IdAble;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFailTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.trigger.condition.TaskFinishTriggerCondition;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 任务实体类
 */

public class Task extends BaseObservable implements Updateable, Insertable, Deleteable,ICopy<Task>,Getable,IdAble {


    /**
     * 只能完成一次
     */
    public static final int REP_ONCE = 0;
    /**
     * 可重复完成的
     * <p>
     * 不存在时间间隔{@link Task#repeatInterval}无效
     * <p>
     * 过期时间无效{@link Task#expirationTime}无效
     */
    public static final int REP_CONTINUOUS = 1;
    /**
     * 每X小时重复
     */
    public static final int REP_HOURLY = 2;
    /**
     * 每X天重复
     */
    public static final int REP_DAILY = 3;
    /**
     * 每X星期重复
     */
    public static final int REP_WEEKLY = 4;
    /**
     * 每X月重复
     */
    public static final int REP_MONTHLY = 5;
    /**
     * 每X年重复
     */
    public static final int REP_YEARLY = 6;

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
     * 任务完成后增加相应能力
     * <p>
     * key-能力ID val-增加的点数
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
     * 任务失败后减少相应能力
     * <p>
     * key-能力ID val-增加的点数
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
     * 任务失败后扣除的LP
     */
    private int lostLP;

    /**
     * 触发器列表
     */
    private List<Trigger> triggers=new ArrayList<>();

    {
        //默认初始化成功一次的奖励对象
        TriggerInfo successInfo=new TriggerInfo();
        successInfo.setTriggerCondition(TaskFinishTriggerCondition.class.getName());
        Trigger successTrigger=addTrigger(successInfo);
    }

    {
        //默认初始化失败一次的奖励对象
        TriggerInfo failInfo=new TriggerInfo();
        failInfo.setTriggerCondition(TaskFailTriggerCondition.class.getName());
        Trigger failTrigger=addTrigger(failInfo);

    }



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

        List<Integer> triggerIDs=new ArrayList<>();
        for (Trigger trigger : triggers) {
            Game.getInstance().getTriggerManager().updateTrigger(trigger.getTriggerInfo());
            triggerIDs.add(Long.valueOf(trigger.getTriggerInfo().getId()).intValue());
        }
        cv.put("triggers",FormatUtil.list2Str(triggerIDs));

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

    public int getXp() {
        Trigger successTrigger = getSuccessTrigger();
        return successTrigger.getTriggerInfo().getXp();
    }

    public void setXp(int xp) {
        Trigger successTrigger = getSuccessTrigger();
        successTrigger.getTriggerInfo().setXp(xp);
        notifyPropertyChanged(BR.xp);
    }

    @Bindable
    public Map<Long, Integer> getSuccessSkills() {
        Trigger successTrigger = getSuccessTrigger();
        return successTrigger.getTriggerInfo().getSkills();
    }

    public void setSuccessSkills(Map<Long, Integer> successSkills) {
        Trigger successTrigger = getSuccessTrigger();
        successTrigger.getTriggerInfo().setSkills(successSkills);
        notifyPropertyChanged(BR.successSkills);
    }

    @Bindable
    public List<RandomItemReward> getSuccessItems() {
        Trigger successTrigger = getSuccessTrigger();
        return successTrigger.getTriggerInfo().getItems();
    }

    public void setSuccessItems(List<RandomItemReward> successItems) {
        Trigger successTrigger = getSuccessTrigger();
        successTrigger.getTriggerInfo().setItems(successItems);
        notifyPropertyChanged(BR.successItems);
    }

    @Bindable
    public List<AchievementReward> getSuccessAchievements() {
        Trigger successTrigger = getSuccessTrigger();
        return successTrigger.getTriggerInfo().getAchievements();
    }

    @Bindable
    public Map<Long, Integer> getFailureSkills() {
        Trigger failTrigger = getFailTrigger();
        return failTrigger.getTriggerInfo().getSkills();
    }

    public void setFailureSkills(Map<Long, Integer> failureSkills) {
        Trigger failTrigger = getFailTrigger();
        failTrigger.getTriggerInfo().setSkills(failureSkills);
        notifyPropertyChanged(BR.failureSkills);
    }

    @Bindable
    public List<RandomItemReward> getFailureItems() {
        Trigger failTrigger = getFailTrigger();
        return failTrigger.getTriggerInfo().getItems();
    }

    public void setFailureItems(List<RandomItemReward> failureItems) {
        Trigger failTrigger = getFailTrigger();
        failTrigger.getTriggerInfo().setItems(failureItems);
        notifyPropertyChanged(BR.failureItems);
    }

    @Bindable
    public List<AchievementReward> getFailureAchievements() {
        Trigger failTrigger = getFailTrigger();
        return failTrigger.getTriggerInfo().getAchievements();
    }

    public void setFailureAchievements(List<AchievementReward> failureAchievements) {
        Trigger failTrigger = getFailTrigger();
        failTrigger.getTriggerInfo().setAchievements(failureAchievements);
        notifyPropertyChanged(BR.failureAchievements);
    }

    @Bindable
    public int getEarnLP() {
        Trigger successTrigger = getSuccessTrigger();
        return successTrigger.getTriggerInfo().getEarnLP();
    }

    @Bindable
    public int getLostLP() {
        Trigger failTrigger = getFailTrigger();
        return failTrigger.getTriggerInfo().getEarnLP();
    }


    public void setLostLP(int lostLP) {
        Trigger failTrigger = getFailTrigger();
        failTrigger.getTriggerInfo().setEarnLP(lostLP);
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
        Trigger successTrigger = getSuccessTrigger();
        successTrigger.getTriggerInfo().setEarnLP(earnLP);
        notifyPropertyChanged(BR.earnLP);
    }

    public void setSuccessAchievements(List<AchievementReward> successAchievements) {
        Trigger successTrigger = getSuccessTrigger();
        successTrigger.getTriggerInfo().setAchievements(successAchievements);
        notifyPropertyChanged(BR.successAchievements);
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    public void setTriggers(List<TriggerInfo> triggerInfos) {
        //先将现有触发器失效
        for (Trigger trigger : this.triggers) {
            trigger.invalid();
        }

        this.triggers.clear();
        for (TriggerInfo triggerInfo : triggerInfos) {
            addTrigger(triggerInfo);
        }
    }

    public Trigger addTrigger(TriggerInfo info){
        info.setType(TriggerInfo.TYPE_TASK);
        info.setMainObjId(getId());
        Trigger t=new Trigger(info);
        triggers.add(t);
        return t;
    }

    public Trigger getSuccessTrigger(){

        Optional<Trigger> single = Stream.of(triggers).filter(value -> value.getTriggerInfo().getTriggerCondition().equals(TaskFinishTriggerCondition.class.getName())).findFirst();
        Trigger successTrigger;
        if (!single.isPresent()){
            TriggerInfo successInfo=new TriggerInfo();
            successInfo.setTriggerCondition(TaskFinishTriggerCondition.class.getName());
            successTrigger=addTrigger(successInfo);
        }else {
            successTrigger=single.get();
        }

        return successTrigger;
    }

    public Trigger getFailTrigger(){

        Optional<Trigger> single = Stream.of(triggers).filter(value -> value.getTriggerInfo().getTriggerCondition().equals(TaskFailTriggerCondition.class.getName())).findFirst();
        Trigger failTrigger;
        if (!single.isPresent()){
            TriggerInfo failInfo=new TriggerInfo();
            failInfo.setTriggerCondition(TaskFailTriggerCondition.class.getName());
            failTrigger=addTrigger(failInfo);
        }else {
            failTrigger=single.get();
        }

        return failTrigger;
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

        List<Integer> triggerIDs=new ArrayList<>();
        for (Trigger trigger : triggers) {
            TriggerInfo triggerInfo = trigger.getTriggerInfo();
            triggerInfo.setMainObjId(getId());
            triggerInfo.setType(TriggerInfo.TYPE_TASK);
            Game.getInstance().getTriggerManager().addTrigger(triggerInfo);
            triggerIDs.add(Long.valueOf(triggerInfo.getId()).intValue());
        }
        cv.put("triggers",FormatUtil.list2Str(triggerIDs));
//
//        cv.put("failureSkills", FormatUtil.skillMap2Str(getFailureSkills()));
//        cv.put("failureItems", FormatUtil.itemRewardList2Str(getFailureItems()));
//        cv.put("failureAchievements", FormatUtil.achievementRewardList2Str(getFailureAchievements()));
//        cv.put("lostLP", getLostLP());
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

        long insertId = sqLiteDatabase.insert(DBHelper.TABLE_TASK, null, cv);

        for (Trigger trigger : triggers) {
            TriggerInfo triggerInfo = trigger.getTriggerInfo();
            triggerInfo.setMainObjId(insertId);
            Game.getInstance().getTriggerManager().updateTrigger(triggerInfo);
        }

        return insertId;
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
        this.setXp(task.getXp());
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

    @Override
    public void getFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor query = sqLiteDatabase.query(DBHelper.TABLE_ITEM, null, "_id = ?", new String[]{String.valueOf(getId())}, null, null, null);
        getFromCursor(query);
        query.close();
    }

    @Override
    public void getFromCursor(Cursor cursor) {

        this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        this.setName(cursor.getString(cursor.getColumnIndex("name")));
        this.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        this.setAutoFail(cursor.getInt(cursor.getColumnIndex("isAutoFail")) == 1);
        this.setIcon(cursor.getString(cursor.getColumnIndex("icon")));

        this.setDifficulty(cursor.getInt(cursor.getColumnIndex("difficulty")));
        this.setFear(cursor.getInt(cursor.getColumnIndex("fear")));
        this.setUrgency(cursor.getInt(cursor.getColumnIndex("urgency")));

        List<Integer> triggerIDs = FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("triggers")));
        triggers.clear();
        ITriggerManager triggerManager = Game.getInstance().getTriggerManager();
        for (Integer triggerID : triggerIDs) {
            Trigger trigger=new Trigger(triggerManager.getTrigger(triggerID));
            triggers.add(trigger);
        }

//        this.setXp(cursor.getInt(cursor.getColumnIndex("xp")));
//        this.setSuccessSkills(FormatUtil.str2SkillMap(cursor.getString(cursor.getColumnIndex("successSkills"))));
//        this.setSuccessItems(FormatUtil.str2ItemRewardList(cursor.getString(cursor.getColumnIndex("successItems"))));
//        this.setSuccessAchievements(FormatUtil.str2achievementRewardList(cursor.getString(cursor.getColumnIndex("successAchievements"))));
//
//        this.setFailureSkills(FormatUtil.str2SkillMap(cursor.getString(cursor.getColumnIndex("failureSkills"))));
//        this.setFailureItems(FormatUtil.str2ItemRewardList(cursor.getString(cursor.getColumnIndex("failureItems"))));
//        this.setFailureAchievements(FormatUtil.str2achievementRewardList(cursor.getString(cursor.getColumnIndex("failureAchievements"))));


//        this.setEarnLP(cursor.getInt(cursor.getColumnIndex("earnLP")));
//        this.setLostLP(cursor.getInt(cursor.getColumnIndex("lostLP")));

        this.setRepeatType(cursor.getInt(cursor.getColumnIndex("repeatType")));
        this.setRepeatInterval(cursor.getInt(cursor.getColumnIndex("repeatInterval")));
        this.setRepeatAvailableTime(cursor.getInt(cursor.getColumnIndex("repeatAvailableTime")));

        String expirationTime = cursor.getString(cursor.getColumnIndex("expirationTime"));
        if (expirationTime != null && !expirationTime.equals("")) {
            try {
                this.setExpirationTime(SimpleDateFormat.getInstance().parse(expirationTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime != null && !createTime.equals("")) {
            try {
                this.setCreateTime(SimpleDateFormat.getInstance().parse(createTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        String updateTime = cursor.getString(cursor.getColumnIndex("updateTime"));
        if (updateTime != null && !updateTime.equals("")) {
            try {
                this.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        this.setCompleteTimes(cursor.getInt(cursor.getColumnIndex("completeTimes")));
        this.setFailureTimes(cursor.getInt(cursor.getColumnIndex("failureTimes")));
        this.setPreTasks(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("preTasks"))));
        this.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));


    }

    public List<Trigger> getTriggers() {
        return triggers;
    }
}
