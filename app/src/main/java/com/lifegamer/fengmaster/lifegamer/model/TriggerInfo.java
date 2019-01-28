package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.annotation.JSONField;
import com.lifegamer.fengmaster.lifegamer.BR;

import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Getable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.model.base.IdAble;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.AchievementReward;
import com.lifegamer.fengmaster.lifegamer.model.randomreward.RandomItemReward;
import com.lifegamer.fengmaster.lifegamer.trigger.Trigger;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;
import com.lifegamer.fengmaster.lifegamer.util.TriggerUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 触发器实体对象
 *
 * 触发器,用于联系各个实体之间的关系
 * Created by FengMaster on 19/01/08.
 */
public class TriggerInfo extends BaseObservable implements Updateable, Insertable, Deleteable,ICopy<TriggerInfo>,Getable,IdAble {

    /**
     * 触发器主体种类-任务
     */
    public static final String TYPE_TASK=Task.class.getSimpleName();

    /**
     * 触发器ID
     */
    private long id;

    /**
     * 触发器主体的种类
     */
    private String type;

    /**
     * 触发器主体的ID
     */
    private long mainObjId;

    /**
     * 触发条件
     * 对应触发器的class
     * @see Trigger 的实现类
     */
    private String triggerCondition;

    /**
     * 触发条件说明
     */
    private String triggerConditionDesc;

    /**
     * 触发参数
     * 传递给触发器的参数
     */
    private String triggerParameter;

    /**
     * 触发器 保存的信息
     */
    private String saveInfo;


    /**
     * 触发后获得经验值
     */
    private int xp;

    /**
     * 触发后增加相应能力
     * <p>
     * key-能力ID val-增加的点数
     */
    private Map<Long, Integer> skills=new HashMap<>();
    /**
     * 触发后获得的物品列表
     */
    private List<RandomItemReward> items=new ArrayList<>();
    /**
     * 触发后获得的成就列表
     */
    private List<AchievementReward> achievements=new ArrayList<>();

    /**
     * 触发后奖励的LP
     */
    private int earnLP;


    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 更新时间
     */
    private Date updateTime=new Date();


    @Override
    public void copyFrom(TriggerInfo triggerInfo) {
        this.setId(triggerInfo.getId());
        this.setType(triggerInfo.getType());
        this.setTriggerCondition(triggerInfo.getTriggerCondition());
        this.setTriggerConditionDesc(triggerInfo.getTriggerConditionDesc());
        this.setTriggerParameter(triggerInfo.getTriggerParameter());
        this.setItems(triggerInfo.getItems());
        this.setAchievements(triggerInfo.getAchievements());
        this.setSkills(triggerInfo.getSkills());
        this.setEarnLP(triggerInfo.getEarnLP());
        this.setMainObjId(triggerInfo.getMainObjId());
        this.setXp(triggerInfo.getXp());
        this.setCreateTime(triggerInfo.getCreateTime());
        this.setUpdateTime(triggerInfo.getUpdateTime());
    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_TRIGGER, "_id = ?", new String[]{String.valueOf(getId())});
    }

    @Override
    public void getFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor query = sqLiteDatabase.query(DBHelper.TABLE_TRIGGER, null, "_id = ?", new String[]{String.valueOf(getId())}, null, null, null);
        getFromCursor(query);
        query.close();
    }

    @Override
    public void getFromCursor(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        this.setXp(cursor.getInt(cursor.getColumnIndex("xp")));
        this.setType(cursor.getString(cursor.getColumnIndex("type")));
        this.setMainObjId(cursor.getInt(cursor.getColumnIndex("mainObjId")));
        this.setEarnLP(cursor.getInt(cursor.getColumnIndex("earnLP")));
        this.setTriggerCondition(cursor.getString(cursor.getColumnIndex("triggerCondition")));
        this.setTriggerConditionDesc(cursor.getString(cursor.getColumnIndex("triggerConditionDesc")));
        this.setTriggerParameter(cursor.getString(cursor.getColumnIndex("triggerParameter")));
        this.setSaveInfo(cursor.getString(cursor.getColumnIndex("saveInfo")));

        this.setSkills(FormatUtil.str2SkillMap(cursor.getString(cursor.getColumnIndex("skills"))));
        this.setItems(FormatUtil.str2ItemRewardList(cursor.getString(cursor.getColumnIndex("items"))));
        this.setAchievements(FormatUtil.str2achievementRewardList(cursor.getString(cursor.getColumnIndex("achievements"))));

        try {
            this.setCreateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("createTime"))));
            this.setUpdateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("updateTime"))));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {

        ContentValues cv = new ContentValues();
        if (getId()!=0){
            cv.put("_id",getId());
        }
        cv.put("type", getType());
        cv.put("mainObjId", getMainObjId());
        cv.put("triggerCondition", getTriggerCondition());
        cv.put("triggerConditionDesc", getTriggerConditionDesc());

        cv.put("triggerParameter", getTriggerParameter());
        cv.put("xp", getXp());
        cv.put("saveInfo", getSaveInfo());
        cv.put("earnLP", getEarnLP());

        cv.put("skills", FormatUtil.skillMap2Str(getSkills()));
        cv.put("items", FormatUtil.itemRewardList2Str(getItems()));
        cv.put("achievements", FormatUtil.achievementRewardList2Str(getAchievements()));

        if (createTime!=null){
            cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        }else {
            cv.put("createTime","");
        }

        if (updateTime!=null){
            cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));
        }else {
            cv.put("updateTime","");
        }
        return sqLiteDatabase.insert(DBHelper.TABLE_TRIGGER, null, cv);
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("type", getType());
        cv.put("mainObjId", getMainObjId());
        cv.put("triggerCondition", getTriggerCondition());
        cv.put("triggerConditionDesc", getTriggerConditionDesc());

        cv.put("triggerParameter", getTriggerParameter());
        cv.put("xp", getXp());
        cv.put("saveInfo", getSaveInfo());
        cv.put("earnLP", getEarnLP());

        cv.put("skills", FormatUtil.skillMap2Str(getSkills()));
        cv.put("items", FormatUtil.itemRewardList2Str(getItems()));
        cv.put("achievements", FormatUtil.achievementRewardList2Str(getAchievements()));

        if (createTime!=null){
            cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        }else {
            cv.put("createTime","");
        }

        if (updateTime!=null){
            cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));
        }else {
            cv.put("updateTime","");
        }

        return sqLiteDatabase.update(DBHelper.TABLE_TRIGGER, cv, "_id = ?", new String[]{String.valueOf(getId())});
    }

    @Override
    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public long getMainObjId() {
        return mainObjId;
    }

    public void setMainObjId(long mainObjId) {
        this.mainObjId = mainObjId;
    }

    @Bindable
    public String getTriggerCondition() {
        return triggerCondition;
    }

    public void setTriggerCondition(String triggerCondition) {
        this.triggerCondition = triggerCondition;
        setTriggerConditionDesc(TriggerUtil.getTriggerDesc(triggerCondition));
        notifyPropertyChanged(BR.triggerCondition);
    }
    @Bindable
    public String getTriggerParameter() {
        return triggerParameter;
    }

    public void setTriggerParameter(String triggerParameter) {
        this.triggerParameter = triggerParameter;
        notifyPropertyChanged(BR.triggerParameter);
    }

    @Bindable
    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
        notifyPropertyChanged(BR.xp);
    }
    @JSONField(serialize = false)
    @Bindable
    public Map<Long, Integer> getSkills() {
        return skills;
    }

    public void setSkills(Map<Long, Integer> skills) {
        this.skills = skills;
        notifyPropertyChanged(BR.skills);
    }

    @JSONField(name = "skills")
    public String getSkillsStr(){
        return FormatUtil.skillMap2Str(getSkills());
    }

    @JSONField(name = "skills")
    public void setSkillsStr(String str){
        setSkills(FormatUtil.str2SkillMap(str));
    }



    @JSONField(serialize = false)
    @Bindable
    public List<RandomItemReward> getItems() {
        return items;
    }

    public void setItems(List<RandomItemReward> items) {
        this.items = items;
        notifyPropertyChanged(BR.items);
    }

    @JSONField(name = "items")
    public String getItemsStr(){
        return FormatUtil.itemRewardList2Str(getItems());
    }

    @JSONField(name = "items")
    public void setItemsStr(String str){
        setItems(FormatUtil.str2ItemRewardList(str));
    }




    @JSONField(serialize = false)
    @Bindable
    public List<AchievementReward> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementReward> achievements) {
        this.achievements = achievements;
        notifyPropertyChanged(BR.achievements);
    }


    @JSONField(name = "achievements")
    public String getAchievementsStr(){
        return FormatUtil.achievementRewardList2Str(getAchievements());
    }

    @JSONField(name = "achievements")
    public void setAchievementsStr(String str){
        setAchievements(FormatUtil.str2achievementRewardList(str));
    }

    @Bindable
    public int getEarnLP() {
        return earnLP;
    }

    public void setEarnLP(int earnLP) {
        this.earnLP = earnLP;
        notifyPropertyChanged(BR.earnLP);
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
    @Bindable
    public String getSaveInfo() {
        return saveInfo;
    }

    public void setSaveInfo(String saveInfo) {
        this.saveInfo = saveInfo;
        notifyPropertyChanged(BR.saveInfo);
    }
    @Bindable
    public String getTriggerConditionDesc() {
        return triggerConditionDesc;
    }

    public void setTriggerConditionDesc(String triggerConditionDesc) {
        this.triggerConditionDesc = triggerConditionDesc;
        notifyPropertyChanged(BR.triggerConditionDesc);
    }

    @Override
    public boolean equals(Object obj) {
        if (id==0&&((TriggerInfo)obj).id==0){
            return super.equals(obj);
        }else if (id!=0&&((TriggerInfo)obj).id!=0){
            return id==((TriggerInfo)obj).id;
        }else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        if (id==0){
            return super.hashCode();
        }else {
            return Long.valueOf(id).hashCode();
        }
    }
}
