package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;

import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Getable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志实体对象
 * <p>
 * PS:所有属性写入日志的时候使用可读中文表示,不使用数字或者其他表示
 */
public class Log extends BaseObservable implements Insertable, ICopy<Log>, Getable,Updateable,Deleteable {

    /**
     * 日志ID
     */
    private long id;

    /**
     * 日志种类
     * 包括:
     * 任务
     * 物品
     * 成就
     * 能力
     * 经验
     * LP点数
     */
    private String type;
    /**
     * 动作种类
     * 包括:
     * 完成
     * 失败
     * 获得
     * 失去
     * 增加
     * 减少
     */
    private String action;
    /**
     * 操作对象的名称:
     * 如:
     * type=成就
     * operName=成就1
     * <p>
     * 表示操作对象是成就1
     */
    private String operName;


    /**
     * opername对象的ID
     */
    private String operId;
    /**
     * 动作操作对象的对应属性名:
     * 如:
     */
    private String property;


    /**
     * 本次操作的值
     */
    private String value;

    /**
     * 新取值
     */
    private String newValue;
    /**
     * 旧取值
     */
    private String oldValue;
    /**
     * 额外信息
     */
    private String extMessage;
    /**
     * 额外数值
     */
    private String extValue;

    /**
     * 事件标号
     */
    private int eventSequence;

    /**
     * 日志时间
     */
    private Date logTime;

    @Override
    public void copyFrom(Log log) {
        this.action=log.action;
        this.extMessage=log.extMessage;
        this.extValue=log.extValue;
        this.logTime=log.logTime;
        this.newValue=log.newValue;
        this.value=log.value;
        this.oldValue=log.oldValue;
        this.operName=log.operName;
        this.operId=log.operId;
        this.property=log.property;
        this.eventSequence=log.eventSequence;
        this.type=log.type;
    }

    @Override
    public void getFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor query = sqLiteDatabase.query(DBHelper.TABLE_LOG, null, "_id = ?", new String[]{String.valueOf(getId())}, null, null, null);
        getFromCursor(query);
        query.close();
    }

    @Override
    public void getFromCursor(Cursor cursor) {
        this.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        this.setAction(cursor.getString(cursor.getColumnIndex("action")));
        this.setType(cursor.getString(cursor.getColumnIndex("type")));
        this.setOperName(cursor.getString(cursor.getColumnIndex("operName")));
        this.setOperId(cursor.getString(cursor.getColumnIndex("operId")));
        this.setProperty(cursor.getString(cursor.getColumnIndex("property")));
        this.setValue(cursor.getString(cursor.getColumnIndex("value")));
        this.setNewValue(cursor.getString(cursor.getColumnIndex("newValue")));
        this.setOldValue(cursor.getString(cursor.getColumnIndex("oldValue")));
        this.setExtMessage(cursor.getString(cursor.getColumnIndex("extMessage")));
        this.setExtValue(cursor.getString(cursor.getColumnIndex("extValue")));
        this.setEventSequence(cursor.getInt(cursor.getColumnIndex("eventSequence")));
        String logTime = cursor.getString(cursor.getColumnIndex("logTime"));
        if (logTime != null && !logTime.equals("")) {
            try {
                this.setLogTime(SimpleDateFormat.getInstance().parse(cursor.getString(cursor.getColumnIndex("logTime"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("type", getType());
        cv.put("action", getAction());
        cv.put("property",getProperty());
        cv.put("operName",getOperName());
        cv.put("operId",getOperId());
        cv.put("value", getValue());
        cv.put("newValue", getNewValue());
        cv.put("oldValue", getOldValue());
        cv.put("extMessage", getExtMessage());
        cv.put("extValue", getExtValue());
        cv.put("eventSequence", getEventSequence());
        if (getLogTime()==null){
            cv.put("logTime", SimpleDateFormat.getInstance().format(new Date()));
        }else {
            cv.put("logTime", SimpleDateFormat.getInstance().format(getLogTime()));
        }
        return sqLiteDatabase.insert(DBHelper.TABLE_LOG, null, cv);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getExtMessage() {
        return extMessage;
    }

    public void setExtMessage(String extMessage) {
        this.extMessage = extMessage;
    }

    public String getExtValue() {
        return extValue;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public int getEventSequence() {
        return eventSequence;
    }

    public void setEventSequence(int eventSequence) {
        this.eventSequence = eventSequence;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperId() {
        return operId;
    }

    public void setOperId(String operId) {
        this.operId = operId;
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("action", getAction());
        cv.put("type", getType());
        cv.put("property", getProperty());
        cv.put("value", getValue());
        cv.put("operName", getOperName());
        cv.put("operId", getOperId());
        cv.put("eventSequence", getEventSequence());
        cv.put("oldValue", getOldValue());
        cv.put("newValue", getNewValue());
        cv.put("extValue", getExtValue());
        cv.put("extMessage", getExtMessage());

        if (logTime!=null){
            cv.put("logTime", SimpleDateFormat.getInstance().format(getLogTime()));
        }else {
            cv.put("logTime",SimpleDateFormat.getInstance().format(new Date()));
        }


        return sqLiteDatabase.update(DBHelper.TABLE_LOG, cv, "_id = ?", new String[]{String.valueOf(getId())});

    }

    @Override
    public int delete(SQLiteDatabase sqLiteDatabase) {
        return sqLiteDatabase.delete(DBHelper.TABLE_LOG, "_id = ?", new String[]{String.valueOf(getId())});

    }

    public static class TYPE {
        public static final String TASK = "任务";
        public static final String HERO = "玩家";
        public static final String SKILL = "能力";
        public static final String ACHIEVEMENT = "成就";
        public static final String ITEM = "物品";
        public static final String REWARDITEM = "奖励";

    }

    public static class ACTION {
        public static final String ADD = "增加";
        public static final String SUB = "减少";
        public static final String EDIT = "编辑";
        public static final String FINISH = "完成";
        public static final String FAIL = "失败";
        public static final String GET = "取得";
        public static final String LOSE = "失去";

    }

    public static class PROPERTY {
        public static final String DEFAULT = " ";
        public static final String XP = "经验";
        public static final String LEVEL = "等级";
        public static final String TASK = "任务";
        public static final String LIFEPOINT = "金币";
        public static final String QUANTITY = "数量";

    }


    public static class ORDER{
        public static final String AFTER="after";
        public static final String BEFORE="before";
    }
}
