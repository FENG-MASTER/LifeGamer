package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.lifegamer.fengmaster.lifegamer.App;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IAchievementManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IItemManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.INoteManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ISkillManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.ITaskManager;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IWealthManager;

/**
 * Created by qianzise on 2017/10/4.
 */

public class Hero implements Insertable,Updateable{


    private String name;
    private int id ;
    private String title;
    private String introduction;
    private String avatarUrl;

    private int level;
    private int xp;
    private int upGradeXP;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void setUpGradeXP(int upGradeXP) {
        this.upGradeXP = upGradeXP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("title",getTitle());
        cv.put("introduction",getIntroduction());
        cv.put("avatar",getAvatarUrl());
        cv.put("level",getLevel());
        cv.put("xp",getXp());
        cv.put("upGradeXP",getUpGradeXP());
        return sqLiteDatabase.update(DBHelper.TABLE_HERO,cv,"_id =?",new String[]{String.valueOf(getId())});
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("name",getName());
        cv.put("title",getTitle());
        cv.put("introduction",getIntroduction());
        cv.put("avatar",getAvatarUrl());
        cv.put("level",getLevel());
        cv.put("xp",getXp());
        cv.put("upGradeXP",getUpGradeXP());
        return sqLiteDatabase.insert(DBHelper.TABLE_HERO,null,cv);
    }

    public static Hero emptyHero =new Hero();

    static {
        emptyHero.setTitle(App.getContext().getString(R.string.empty));
        emptyHero.setName(App.getContext().getString(R.string.empty));
        emptyHero.setIntroduction(App.getContext().getString(R.string.empty));
        emptyHero.setAvatarUrl(App.getContext().getString(R.string.empty));
    }

}
