package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 成就实体类
 */

public class Achievement implements Insertable,Updateable,Deleteable{

    /**
     * 成就ID
     */
    private long id;

    /**
     * 成就名称
     */
    private String name;

    /**
     * 成就分类
     */
    private String type;

    /**
     * 成就图标
     */
    private String icon;

    /**
     * 成就详细描述
     */
    private String desc;

    /**
     * 是否已经获得
     */
    private boolean isGot;

    /**
     * 成就获得时间
     */
    private Date gainTime;

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

    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isGot() {
        return isGot;
    }

    public void setGot(boolean got) {
        isGot = got;
    }

    public Date getGainTime() {
        return gainTime;
    }

    public void setGainTime(Date gainTime) {
        this.gainTime = gainTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_ACHIEVEMENT,"_id =?",new String[]{String.valueOf(getId())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("type",getType());
        cv.put("icon",getIcon());
        cv.put("desc",getDesc());
        cv.put("isGot",isGot());
        cv.put("gainTime", SimpleDateFormat.getInstance().format(getGainTime()));
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime",SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_ACHIEVEMENT,cv,"_id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("type",getType());
        cv.put("icon",getIcon());
        cv.put("desc",getDesc());
        cv.put("isGot",isGot());
        cv.put("gainTime", getGainTime()!=null?SimpleDateFormat.getInstance().format(getGainTime()):null);
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime",SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.insert(DBHelper.TABLE_ACHIEVEMENT,null,cv);
    }
}
