package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 笔记对象,暂时只支持文本格式的笔记
 */

public class Note implements Insertable,Deleteable,Updateable{

    private int ID;

    private String text;

    private Date createTime;

    private Date updateTime;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_NOTE,"_id = ?",new String[]{String.valueOf(getID())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("text",getText());
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_NOTE,cv,"_id = ?",new String[]{String.valueOf(getID())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("text",getText());
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));
        
        return sqLiteDatabase.insert(DBHelper.TABLE_NOTE,null,cv);
    }
}
