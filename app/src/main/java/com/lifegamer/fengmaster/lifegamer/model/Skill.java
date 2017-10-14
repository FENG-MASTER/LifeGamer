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
import com.lifegamer.fengmaster.lifegamer.strategy.xp.ILevelXP;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.NormalLevelXP;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 技能对象
 */
public class Skill extends BaseObservable implements Insertable, Updateable, Deleteable, ICopy<Skill> {

    private static final int DEF_UPGRADE_XP=1000;

    /**
     * 经验曲线模块
     */
    private static ILevelXP levelXP = new NormalLevelXP();
    /**
     * 技能id
     */
    private long id;
    /**
     * 技能名字
     */
    private String name;
    /**
     * 技能当前的经验
     */
    private int XP;
    /**
     * 技能当前等级
     */
    private int level = 1;
    /**
     * 技能所属种类
     */
    private String type;
    /**
     * 技能简介
     */
    private String intro;
    /**
     * 技能升级所需XP
     */
    private int upGradeXP=DEF_UPGRADE_XP;
    /**
     * 技能图标
     */
    private String icon;
    /**
     * 笔记ID
     */
    private List<Integer> notes;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 更新时间
     */
    private Date updateTime = new Date();

    public static void setLevelXP(ILevelXP levelXP) {
        Skill.levelXP = levelXP;
    }

    public void addXP(int XP) {
        this.XP += XP;
        if (this.XP >= getUpGradeXP()) {
            //升级
            this.XP -= getUpGradeXP();
            levelUp();
        }
        notifyPropertyChanged(BR.xp);
        updateUpdateTime();

    }

    @Bindable
    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void levelUp() {
        this.level++;
        setUpGradeXP(levelXP.getXP(getLevel()));
        notifyPropertyChanged(BR.level);
    }

    @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (this.level==level){
            return;
        }
        this.level = level;
        notifyPropertyChanged(BR.level);
        updateUpdateTime();

    }

    public void setUpGradeXP(int upGradeXP) {
        if (this.upGradeXP==upGradeXP){
            return;
        }
        this.upGradeXP = upGradeXP;
        notifyPropertyChanged(BR.upGradeXP);
        updateUpdateTime();

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Skill) obj).getName());
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name!=null&&name.equals(this.name)){
            return;
        }
        this.name = name;
        notifyPropertyChanged(BR.name);
        updateUpdateTime();

    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_SKILL, "_id = ?", new String[]{String.valueOf(getId())});
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
        updateUpdateTime();

    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("level", getLevel());
        cv.put("xp", getXP());
        cv.put("upGradeXP", getUpGradeXP());
        cv.put("type", getType());
        cv.put("intro", getIntro());
        cv.put("icon", getIcon());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_SKILL, cv, "_id = ?", new String[]{String.valueOf(getId())});
    }

    @Bindable
    public int getXP() {
        return XP;
    }

    @Bindable
    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type!=null&&type.equals(this.type)){
            return;
        }
        this.type = type;
        notifyPropertyChanged(BR.type);
        updateUpdateTime();

    }

    @Bindable
    public String getIntro() {
        return intro;
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        if (icon!=null&&icon.equals(this.icon)){
            return;
        }
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
        updateUpdateTime();

    }

    @Bindable
    public List<Integer> getNotes() {
        return notes;
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

    private void updateUpdateTime(){
        setUpdateTime(new Date());
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
        updateUpdateTime();
    }

    public void setIntro(String intro) {
        if (intro!=null&&intro.equals(this.intro)){
            return;
        }
        this.intro = intro;
        notifyPropertyChanged(BR.intro);
        updateUpdateTime();

    }

    public void setXP(int XP) {
        if (XP==this.XP){
            return;
        }
        this.XP = XP;
        notifyPropertyChanged(BR.xp);
        updateUpdateTime();

    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("level", getLevel());
        cv.put("xp", getXP());
        cv.put("upGradeXP", getUpGradeXP());
        cv.put("type", getType());
        cv.put("intro", getIntro());
        cv.put("icon", getIcon());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.insert(DBHelper.TABLE_SKILL, null, cv);
    }

    @Override
    public void copyFrom(Skill skill) {
        setName(skill.getName());
        setUpGradeXP(skill.getUpGradeXP());
        setLevel(skill.getLevel());
        setCreateTime(skill.getCreateTime());
        setIcon(skill.getIcon());
        setIntro(skill.getIntro());
        setNotes(skill.getNotes());
        setType(skill.getType());
        setUpdateTime(skill.getUpdateTime());
        setXP(skill.getXP());
    }
}
