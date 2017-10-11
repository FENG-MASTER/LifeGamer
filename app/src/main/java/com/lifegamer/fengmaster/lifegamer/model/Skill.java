package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
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
public class Skill implements Insertable,Updateable,Deleteable,ICopy<Skill>{

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
    private int level;

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
    private int upGradeXP;

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
    private Date createTime=new Date();

    /**
     * 更新时间
     */
    private Date updateTime=new Date();


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void setUpGradeXP(int upGradeXP) {
        this.upGradeXP = upGradeXP;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Skill) obj).getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_SKILL,"_id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("level",getLevel());
        cv.put("xp",getXP());
        cv.put("upGradeXP",getUpGradeXP());
        cv.put("type",getType());
        cv.put("intro",getIntro());
        cv.put("icon",getIcon());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_SKILL,cv,"_id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("level",getLevel());
        cv.put("xp",getXP());
        cv.put("upGradeXP",getUpGradeXP());
        cv.put("type",getType());
        cv.put("intro",getIntro());
        cv.put("icon",getIcon());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.insert(DBHelper.TABLE_SKILL,null,cv);
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
