package com.lifegamer.fengmaster.lifegamer.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.event.GameBaseInitFinish;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.Skill;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by qianzise on 2017/10/5.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper instance;

    public static DBHelper getInstance(){
        if (instance==null){
            synchronized (DBHelper.class){
                if (instance==null){
                    instance=new DBHelper(App.getContext());
                }
            }
        }
        return instance;
    }

    private static final String DB_NAME="lifeGamer.db";

    public static final String TABLE_HERO="Hero";
    public static final String TABLE_ITEM="Items";
    public static final String TABLE_TASK="Tasks";
    public static final String TABLE_ACHIEVEMENT="Achievement";
    public static final String TABLE_NOTE="Notes";
    public static final String TABLE_REWARD="Rewards";
    public static final String TABLE_SKILL="Skills";
    public static final String TABLE_LOG="Logs";
    public static final String TABLE_TRIGGER="Trigger";




    /**
     * 创建 英雄 表
     */
    private static final String CREATE_TABLE_HERO="create table if not exists "+TABLE_HERO+
            "( _id integer primary key autoincrement," +
            "name varchar," +
            "title varchar,"+
            "level integer," +
            "xp integer," +
            "lifePoint integer," +
            "upGradeXP integer," +
            "introduction varchar," +
            "avatar varchar)";

    /**
     * 创建 能力 表
     */
    private static final String CREATE_TABLE_SKILL="create table if not exists "+TABLE_SKILL+
            "( _id integer primary key autoincrement," +
            "name varchar unique ," +
            "xp integer," +
            "level integer," +
            "type varchar," +
            "intro varchar," +
            "upGradeXP integer," +
            "icon varchar," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 物品 表
     */
    private static final String CREATE_TABLE_ITEM="create table if not exists "+TABLE_ITEM+
            "( _id integer primary key autoincrement," +
            "name varchar unique," +
            "desc varchar," +
            "type varchar,"+
            "quantity integer," +
            "icon varchar," +
            "expendable boolean," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 成就 表
     */
    private static final String CREATE_TABLE_ACHIEVEMENT="create table if not exists "+TABLE_ACHIEVEMENT+
            "( _id integer primary key autoincrement," +
            "name varchar unique," +
            "type varchar," +
            "icon varchar," +
            "desc varchar," +
            "isGot boolean," +
            "gainTime integer," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 奖励 表
     */
    private static final String CREATE_TABLE_REWARD="create table if not exists "+TABLE_REWARD+
            "( _id integer primary key autoincrement," +
            "itemId integer," +
            "costLPIncrement integer," +
            "addToItem boolean," +
            "notes text," +
            "costLP integer," +
            "quantityAvailable integer," +
            "gainTimes integer," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 笔记 表
     */
    private static final String CREATE_TABLE_NOTE="create table if not exists "+TABLE_NOTE+
            "( _id integer primary key autoincrement," +
            "text text," +
            "crateTime integer," +
            "updateTime integer)";

    /**
     * 创建 任务 表
     */
    private static final String CREATE_TABLE_TASK="create table if not exists "+TABLE_TASK+
            "( _id integer primary key autoincrement," +
            "name varchar unique," +
            "desc varchar," +
            "isAutoFail boolean," +
            "icon varchar," +
            "difficulty integer," +
            "urgency integer," +
            "fear integer," +
            "xp integer,"+
            "successSkills text," +
            "successItems text," +
            "successAchievements text," +
            "failureSkills text," +
            "failureItems text," +
            "failureAchievements text," +
            "earnLP integer," +
            "lostLP integer," +
            "repeatType integer," +
            "repeatInterval integer," +
            "repeatAvailableTime integer," +
            "expirationTime integer," +
            "completeTimes integer," +
            "failureTimes integer," +
            "preTasks text," +
            "notes text," +
            "createTime integer," +
            "updateTime integer)";

    /**
     * 创建 日志 表
     */
    private static final String CREATE_TABLE_LOG="create table if not exists "+TABLE_LOG+
            "( _id integer primary key autoincrement," +
            "type varchar," +
            "property varchar,"+
            "action varchar," +
            "operName varchar," +
            "operId varchar," +
            "value varchar," +
            "newValue varchar," +
            "oldValue varchar," +
            "extMessage varchar," +
            "extValue varchar," +
            "eventSequence integer," +
            "logTime integer)";


    /**
     * 创建 触发器 表
     */
    private static final String CREATE_TABLE_TRIGGER="create table if not exists "+TABLE_TRIGGER+
            "( _id integer primary key autoincrement," +
            "type varchar," +
            "mainObjId integer," +
            "triggerCondition varchar," +
            "triggerParameter varchar," +
            "xp integer," +
            "skills text," +
            "items text," +
            "achievements text," +
            "saveInfo text," +
            "earnLP integer," +
            "createTime integer," +
            "updateTime integer)";



    private DBHelper(Context context){
        super(context,DB_NAME,null,1);
        EventBus.getDefault().register(this);
    }


    private DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, version);
        EventBus.getDefault().register(this);

    }

    private DBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, version, errorHandler);
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void appInitFinish(GameBaseInitFinish gameBaseInitFinish){
        DefaultDBData.try2InitDBData();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_HERO);
        sqLiteDatabase.execSQL(CREATE_TABLE_SKILL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ITEM);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACHIEVEMENT);
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTE);
        sqLiteDatabase.execSQL(CREATE_TABLE_REWARD);
        sqLiteDatabase.execSQL(CREATE_TABLE_TASK);
        sqLiteDatabase.execSQL(CREATE_TABLE_LOG);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRIGGER);

    }





    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }





}
