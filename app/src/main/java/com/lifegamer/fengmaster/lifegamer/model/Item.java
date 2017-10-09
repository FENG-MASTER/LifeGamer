package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
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
 * 物品对象
 */
public class Item extends BaseObservable implements Insertable,Deleteable,Updateable{

    /**
     * 物品ID
     */
    private int id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品详细描述
     */
    private String desc;

    /**
     * 数量
     */
    private int quantity;

    /**
     * 物品图标
     */
    private String icon;

    /**
     * 是否属于消耗品
     */
    private boolean expendable;

    /**
     * 创建物品时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 笔记ID
     */
    private List<Integer> notes;

    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isExpendable() {
        return expendable;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
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
        return sqLiteDatabase.delete(DBHelper.TABLE_ITEM,"_id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("desc",getDesc());
        cv.put("quantity",getQuantity());
        cv.put("icon",getIcon());
        cv.put("expendable",isExpendable());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_ITEM,cv,"_id =?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("desc",getDesc());
        cv.put("quantity",getQuantity());
        cv.put("icon",getIcon());
        cv.put("expendable",isExpendable());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.insert(DBHelper.TABLE_ITEM,null,cv);
    }
}
