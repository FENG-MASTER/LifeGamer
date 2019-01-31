package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Getable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.model.base.IdAble;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 成就实体类
 *
 * 额外说明:
 *  1. 成就只有两种状态,获得和未获得
 *
 */

public class Achievement  extends BaseObservable implements Insertable,Updateable,Deleteable,ICopy<Achievement>,Getable,IdAble {

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
    private String type="未分类";

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
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
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
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);

    }

    @Bindable
    public boolean isGot() {
        return isGot;
    }

    public void setGot(boolean got) {
        isGot = got;
        notifyPropertyChanged(BR.got);

    }

    @Bindable
    public Date getGainTime() {
        return gainTime;
    }

    public void setGainTime(Date gainTime) {
        this.gainTime = gainTime;
        notifyPropertyChanged(BR.gainTimes);

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
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);

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
        long insert = sqLiteDatabase.insert(DBHelper.TABLE_ACHIEVEMENT, null, cv);
        setId(insert);
        return insert;
    }

    @Override
    public void copyFrom(Achievement achievement) {
        this.setId(achievement.getId());
        this.setName(achievement.getName());
        this.setIcon(achievement.getIcon());
        this.setDesc(achievement.getDesc());
        this.setGainTime(achievement.getGainTime());
        this.setGot(achievement.isGot());
        this.setUpdateTime(achievement.getUpdateTime());
        this.setType(achievement.getType());
        this.setCreateTime(achievement.getCreateTime());
        this.setNotes(achievement.getNotes());
    }

    @Override
    public void getFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor query = sqLiteDatabase.query(DBHelper.TABLE_ACHIEVEMENT, null, "_id = ?", new String[]{String.valueOf(getId())}, null, null, null);
        getFromCursor(query);
        query.close();
    }

    @Override
    public void getFromCursor(Cursor cursor) {
        this.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        this.setName(cursor.getString(cursor.getColumnIndex("name")));
        this.setType(cursor.getString(cursor.getColumnIndex("type")));
        this.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        this.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
        this.setGot(cursor.getInt(cursor.getColumnIndex("isGot"))==1);

        this.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));


        String gainTime = cursor.getString(cursor.getColumnIndex("gainTime"));
        if (gainTime != null && !gainTime.equals("")) {
            try {
                this.setGainTime(SimpleDateFormat.getInstance().parse(gainTime));
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
        if (updateTime != null && updateTime.equals("")) {
            try {
                this.setUpdateTime(SimpleDateFormat.getInstance().parse(updateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}
