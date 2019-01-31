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
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.model.base.IdAble;
import com.lifegamer.fengmaster.lifegamer.util.FormatUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 物品对象
 */
public class Item extends BaseObservable implements Insertable, Deleteable, Updateable,ICopy<Item>,Getable,IdAble {

    /**
     * 物品ID
     */
    private long id;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品种类
     */
    private String type="未分类";

    /**
     * 物品详细描述
     */
    private String desc;

    /**
     * 存储数量
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
    private Date createTime=new Date();

    /**
     * 更新时间
     */
    private Date updateTime=new Date();

    /**
     * 笔记ID
     */
    private List<Integer> notes;

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_ITEM, "_id = ?", new String[]{String.valueOf(getId())});
    }

    @Bindable
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("desc", getDesc());
        cv.put("quantity", getQuantity());
        cv.put("icon", getIcon());
        cv.put("expendable", isExpendable());
        cv.put("type",getType());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));

        return sqLiteDatabase.update(DBHelper.TABLE_ITEM, cv, "_id =?", new String[]{String.valueOf(getId())});
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

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Bindable
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        notifyPropertyChanged(BR.quantity);
    }

    /**
     * 物品数量增加
     * @param add
     */
    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.ADD,property = Log.PROPERTY.QUANTITY)
    public void addQuantity(int add){
        this.quantity+=add;
        notifyPropertyChanged(BR.quantityAvailable);
    }

    /**
     * 物品数量减少
     * @param sub
     */
    @LogPoint(type = Log.TYPE.ITEM,action = Log.ACTION.SUB,property = Log.PROPERTY.QUANTITY)
    public void reduceQuantity(int sub){
        this.quantity-=sub;
        notifyPropertyChanged(BR.quantityAvailable);
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
    public boolean isExpendable() {
        return expendable;
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

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
        notifyPropertyChanged(BR.expirationTime);
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("desc", getDesc());
        cv.put("type",getType());
        cv.put("quantity", getQuantity());
        cv.put("icon", getIcon());
        cv.put("expendable", isExpendable());
        cv.put("notes", FormatUtil.list2Str(getNotes()));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime", SimpleDateFormat.getInstance().format(getUpdateTime()));

        long insert = sqLiteDatabase.insert(DBHelper.TABLE_ITEM, null, cv);
        setId(insert);
        return insert;
    }

    @Override
    public void copyFrom(Item item) {
        this.setId(item.getId());
        this.setName(item.getName());
        this.setDesc(item.getDesc());
        this.setExpendable(item.isExpendable());
        this.setType(item.getType());
        this.setIcon(item.getIcon());
        this.setQuantity(item.getQuantity());
        this.setCreateTime(item.getCreateTime());
        this.setUpdateTime(item.getUpdateTime());
        this.setNotes(item.getNotes());

    }

    @Override
    public void getFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor query = sqLiteDatabase.query(DBHelper.TABLE_ITEM, null, "_id = ?", new String[]{String.valueOf(getId())}, null, null, null);
        getFromCursor(query);
        query.close();
    }

    @Override
    public void getFromCursor(Cursor cursor) {

        this.setId(cursor.getLong(cursor.getColumnIndex("_id")));
        this.setName(cursor.getString(cursor.getColumnIndex("name")));
        this.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
        this.setDesc(cursor.getString(cursor.getColumnIndex("desc")));
        this.setExpendable(cursor.getInt(cursor.getColumnIndex("expendable")) == 1);
        this.setType(cursor.getString(cursor.getColumnIndex("type")));
        this.setIcon(cursor.getString(cursor.getColumnIndex("icon")));

        this.setNotes(FormatUtil.str2List(cursor.getString(cursor.getColumnIndex("notes"))));


        String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
        if (createTime != null && !createTime.equals("")) {
            try {
                this.setCreateTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("createTime"))));
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
