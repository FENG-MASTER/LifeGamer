package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.App;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.ILevelXP;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.NormalLevelXP;


/**
 * Created by qianzise on 2017/10/4.
 *
 * 英雄实体类
 */

public class Hero extends BaseObservable implements Insertable,Updateable{

    private static final int DEF_UPGRADE_XP=1000;
    private static final int DEF_LEVEL=1;


    private String name;
    private int id ;
    private String title;
    private String introduction;
    private String avatarUrl;

    private int level;
    private int xp;
    private int upGradeXP;

    private ILevelXP levelXP=new NormalLevelXP();

    @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (this.level!=level){
            setUpGradeXP(levelXP.getXP(level));
        }
        this.level = level;
        notifyPropertyChanged(BR.level);
    }

    public void levelUp(){
        this.level++;
        setUpGradeXP(levelXP.getXP(level));
        notifyPropertyChanged(BR.level);
    }

    @Bindable
    public int getXp() {
        return xp;
    }

    public void addXp(int xp){
        this.xp+=xp;
        if (getXp()>=getUpGradeXP()){
            this.xp-=getUpGradeXP();
            levelUp();
        }
        notifyPropertyChanged(BR.xp);
    }

    public void setXp(int xp) {
        this.xp = xp;
        if (getXp()>=getUpGradeXP()){
            this.xp-=getUpGradeXP();
            levelUp();
        }
        notifyPropertyChanged(BR.xp);
    }
    @Bindable
    public int getUpGradeXP() {
        return upGradeXP;
    }

    public void setUpGradeXP(int upGradeXP) {
        this.upGradeXP = upGradeXP;
        notifyPropertyChanged(BR.upGradeXP);
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
    public int getId() {
        return id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }
    @Bindable
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
        notifyPropertyChanged(BR.introduction);
    }
    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        notifyPropertyChanged(BR.avatarUrl);
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }


    public void setLevelXP(ILevelXP levelXP) {
        this.levelXP = levelXP;
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
        emptyHero.setUpGradeXP(DEF_UPGRADE_XP);
        emptyHero.setLevel(DEF_LEVEL);
    }

}
