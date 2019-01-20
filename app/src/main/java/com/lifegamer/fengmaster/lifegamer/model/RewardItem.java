package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.alibaba.fastjson.annotation.JSONField;
import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
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
 * 奖励实体类
 *
 * 奖励的定位是:玩家<b>购买</b>得到的相应奖励
 *
 * 没有 是否拥有 状态
 */

public class RewardItem extends BaseObservable implements Insertable,Deleteable,Updateable,ICopy<RewardItem>,Getable,IdAble {

    /**
     * 奖励ID
     */
    private long id;

    private long itemId;

    /**
     * 奖励名称
     */
    private String name;

    /**
     * 奖励所属种类
     */
    private String type="未分类";

    /**
     * 奖励详细描述信息
     */
    private String desc;

    /**
     * 获得奖励所需LP点数
     * -1 表示不可购买(只能通过任务获得)
     */
    private int costLP;

    /**
     * 剩余奖励数目
     * -1表示无限
     */
    private int quantityAvailable;

    /**
     * 已经获得的次数
     */
    private int gainTimes;

    /**
     * 创建时间
     */
    private Date createTime=new Date();

    /**
     * 修改时间
     */
    private Date updateTime=new Date();

    /**
     * 奖励图标
     */
    private String icon;

    /**
     * 每次获得奖励后,奖励增加的LP价格
     */
    private int costLPIncrement;

    /**
     * 得到后是否添加到物品
     */
    private boolean addToItem;
    /**
     * 添加到物品后是否属于消耗品
     */
    private boolean expendable;

    /**
     * 笔记ID
     */
    private List<Integer> notes;

    /**
     * 实体item对象
     */
    private Item item;

    public void setItem(Item item) {
        this.item = item;
        notifyPropertyChanged(BR.name);
        notifyPropertyChanged(BR.type);
        notifyPropertyChanged(BR.desc);
        notifyPropertyChanged(BR.icon);

    }

    @JSONField(serialize = false)
    public Item getItem2(){
        if (!addToItem){
            return null;
        }
        if (item!=null){
            return item;
        }else {
            //还没有对应的item,需要去物品管理器中搜索
            item=Game.getInstance().getItemManager().getItem(getName());
            if (item==null){
                //没有对应的item,出错
            }
            return item;
        }
    }

    public Item getItem(){
       return item;
    }

    public Item generateItem(){

        //转换成物品
        Item titem=new Item();
        titem.setName(getName());
        titem.setCreateTime(new Date());
        titem.setExpendable(isExpendable());
        titem.setDesc(getDesc());
        titem.setIcon(getIcon());
        titem.setType(getType());
        titem.setQuantity(0);
        return titem;
    }

    @Bindable
    public boolean isExpendable() {
        return expendable;
    }

    public void setExpendable(boolean expendable) {
        this.expendable = expendable;
        notifyPropertyChanged(BR.expendable);
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
    public String getName() {
        if (item!=null){
            return item.getName();
        }
        return name;
    }

    public void setName(String name) {
        if (item!=null){
            item.setName(name);
        }
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getType() {
        if (item!=null){
            return item.getType();
        }
        return type;
    }

    public void setType(String type) {
        if (item!=null){
            item.setType(type);
        }
        this.type = type;
        notifyPropertyChanged(BR.type);
    }
    @Bindable
    public String getDesc() {
        if (item!=null){
            return item.getDesc();
        }
        return desc;
    }

    public void setDesc(String desc) {
        if (item!=null){
            item.setDesc(desc);
        }
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }
    @Bindable
    public int getCostLP() {
        return costLP;
    }

    public void setCostLP(int costLP) {
        this.costLP = costLP;
        notifyPropertyChanged(BR.costLP);
    }
    @Bindable
    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
        notifyPropertyChanged(BR.quantityAvailable);
    }
    @Bindable
    public int getGainTimes() {
        return gainTimes;
    }

    public void setGainTimes(int gainTimes) {
        this.gainTimes = gainTimes;
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

    public long getItemId() {
        if (item!=null){
            return item.getId();
        }
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
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
    public int getCostLPIncrement() {
        return costLPIncrement;
    }

    public void setCostLPIncrement(int costLPIncrement) {
        this.costLPIncrement = costLPIncrement;
        notifyPropertyChanged(BR.costLPIncrement);
    }
    @Bindable
    public boolean isAddToItem() {
        return addToItem;
    }

    public void setAddToItem(boolean addToItem) {
        this.addToItem = addToItem;
        notifyPropertyChanged(BR.addToItem);
    }
    @Bindable
    public List<Integer> getNotes() {
        return notes;
    }

    public void setNotes(List<Integer> notes) {
        this.notes = notes;
        notifyPropertyChanged(BR.notes);
    }

    @JSONField(serialize = false)
    public boolean isPurchasable(){
        return getCostLP()!=-1&&(getQuantityAvailable()>0||getQuantityAvailable()==-1);
    }




    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_REWARD,"_id = ? ",new String[]{String.valueOf(getId())});
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();

        cv.put("itemId",item.getId());
        cv.put("costLP",getCostLP());
        cv.put("quantityAvailable",getQuantityAvailable());
        cv.put("gainTimes", getGainTimes());
        cv.put("costLPIncrement",getCostLPIncrement());
        cv.put("addToItem",isAddToItem());
        cv.put("notes", FormatUtil.list2Str(notes));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));
        return sqLiteDatabase.update(DBHelper.TABLE_REWARD,cv," _id = ?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();

        cv.put("itemId",item.getId());
        cv.put("costLP",getCostLP());
        cv.put("quantityAvailable",getQuantityAvailable());
        cv.put("gainTimes", getGainTimes());
        cv.put("costLPIncrement",getCostLPIncrement());
        cv.put("addToItem",isAddToItem());
        cv.put("notes", FormatUtil.list2Str(notes));
        cv.put("createTime", SimpleDateFormat.getInstance().format(getCreateTime()));
        cv.put("updateTime",SimpleDateFormat.getInstance().format(getUpdateTime()));


        return sqLiteDatabase.insert(DBHelper.TABLE_REWARD,null,cv);
    }

    @Override
    public void copyFrom(RewardItem rewardItem) {
        this.setId(rewardItem.getId());
        this.setName(rewardItem.getName());
        this.setType(rewardItem.getType());
        this.setDesc(rewardItem.getDesc());
        this.setCostLP(rewardItem.getCostLP());
        this.setQuantityAvailable(rewardItem.getQuantityAvailable());
        this.setGainTimes(rewardItem.getGainTimes());
        this.setIcon(rewardItem.getIcon());
        this.setCostLPIncrement(rewardItem.getCostLPIncrement());
        this.setAddToItem(rewardItem.isAddToItem());
        this.setNotes(rewardItem.getNotes());
        this.setCreateTime(rewardItem.getCreateTime());
        this.setUpdateTime(rewardItem.getUpdateTime());

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
        this.setItemId(cursor.getLong(cursor.getColumnIndex("itemId")));
        this.setCostLP(cursor.getInt(cursor.getColumnIndex("costLP")));
        this.setCostLPIncrement(cursor.getInt(cursor.getColumnIndex("costLPIncrement")));
        this.setGainTimes(cursor.getInt(cursor.getColumnIndex("gainTimes")));
        this.setQuantityAvailable(cursor.getInt(cursor.getColumnIndex("quantityAvailable")));
        this.setAddToItem(cursor.getInt(cursor.getColumnIndex("addToItem")) == 1);
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

    @Override
    public int hashCode() {
        if (name!=null){
            return name.hashCode();
        }else {
            return super.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (name!=null){
            return name.equals(((RewardItem)obj).name);
        }else {
            return super.equals(obj);
        }
    }
}
