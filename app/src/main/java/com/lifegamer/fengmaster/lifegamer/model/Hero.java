package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.App;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;
import com.lifegamer.fengmaster.lifegamer.log.LogPoint;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.ILevelXP;
import com.lifegamer.fengmaster.lifegamer.strategy.xp.NormalLevelXP;
import com.lifegamer.fengmaster.lifegamer.util.DateUtil;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by qianzise on 2017/10/4.
 * <p>
 * 英雄实体类
 * <p>
 * 额外说明:
 * 1. 每次升级,只改变下次升级所需经验值,当前经验值不受影响
 */

public class Hero extends BaseObservable implements Insertable, Updateable {

    private static final int DEF_UPGRADE_XP = 1000;
    private static final int DEF_LEVEL = 1;


    /**
     * 英雄名
     */
    private String name;
    /**
     * ID
     */
    private long id;
    /**
     * 头衔
     */
    private String title;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 头像URL
     */
    private String avatarUrl;

    /**
     * 当前等级
     */
    private int level;
    /**
     * 当前经验值
     */
    private int xp;
    /**
     * 升级所需经验值
     */
    private int upGradeXP;

    /**
     * 体力
     */
    private int bodyPower;

    private int maxBodyPower=24*60;


    private ILevelXP levelXP = new NormalLevelXP();

    private LifePoint lifePoint;


    private Timer timer=new Timer();

    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Game.getInstance().getHeroManager().getHero().notifyPropertyChanged(BR.bodyPower);
                Game.getInstance().getHeroManager().getHero().notifyPropertyChanged(BR.bodyPowerStr);

            }
        },0,60000);
    }

    private NumberFormat nf = NumberFormat.getNumberInstance();
    {
        nf.setMaximumFractionDigits(2);
    }

    @Bindable
    public int getBodyPower() {
        return  DateUtil.getDateDistance(DateUtil.getEndOfToday().getTime(), DateUtil.getNowDate(), Calendar.MINUTE);
    }

    @Bindable
    public String getBodyPowerStr() {
        double v = DateUtil.getDateDistance(DateUtil.getEndOfToday().getTime(), DateUtil.getNowDate(), Calendar.MINUTE) / 60.0;
        return nf.format(v);
    }




    public int getMaxBodyPower() {
        return maxBodyPower;
    }


    @Bindable
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        if (this.level != level) {
            setUpGradeXP(levelXP.getXP(level));
        }
        this.level = level;
        notifyPropertyChanged(BR.level);
    }

    @LogPoint(type = Log.TYPE.HERO, property = Log.PROPERTY.LEVEL, action = Log.ACTION.ADD)
    public void levelUp() {
        this.level++;
        setUpGradeXP(levelXP.getXP(level));
        notifyPropertyChanged(BR.level);
    }

    @LogPoint(type = Log.TYPE.HERO, property = Log.PROPERTY.LEVEL, action = Log.ACTION.SUB)
    public void levelDown() {
        this.level--;
        setUpGradeXP(levelXP.getXP(level));
        notifyPropertyChanged(BR.level);
    }

    @Bindable
    public int getXp() {
        return xp;
    }

    /**
     * 增加经验
     *
     * @param xp
     */
    @LogPoint(type = Log.TYPE.HERO, property = Log.PROPERTY.XP, action = Log.ACTION.ADD)
    public void addXp(int xp) {
        this.xp += xp;
        if (getXp() >= getUpGradeXP()) {
            levelUp();
        }
        notifyPropertyChanged(BR.xp);
    }

    /**
     * 减少经验
     *
     * @param xp
     */
    @LogPoint(type = Log.TYPE.HERO, property = Log.PROPERTY.XP, action = Log.ACTION.SUB)
    public void reduceXp(int xp) {
        this.xp -= xp;
        if (getXp() < getUpGradeXP()) {
            levelDown();
        }
        notifyPropertyChanged(BR.xp);
    }

    public void setXp(int xp) {
        this.xp = xp;
        if (getXp() >= getUpGradeXP()) {
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
    public long getId() {
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

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }


    public void setLevelXP(ILevelXP levelXP) {
        this.levelXP = levelXP;
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("title", getTitle());
        cv.put("introduction", getIntroduction());
        cv.put("avatar", getAvatarUrl());
        cv.put("level", getLevel());
        cv.put("xp", getXp());
        cv.put("upGradeXP", getUpGradeXP());
        return sqLiteDatabase.update(DBHelper.TABLE_HERO, cv, "_id =?", new String[]{String.valueOf(getId())});
    }

    public LifePoint getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(LifePoint lifePoint) {
        this.lifePoint = lifePoint;
    }

    @Override
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put("name", getName());
        cv.put("title", getTitle());
        cv.put("introduction", getIntroduction());
        cv.put("avatar", getAvatarUrl());
        cv.put("level", getLevel());
        cv.put("xp", getXp());
        cv.put("upGradeXP", getUpGradeXP());
        return sqLiteDatabase.insert(DBHelper.TABLE_HERO, null, cv);
    }

    public static Hero emptyHero = new Hero();

    static {
        emptyHero.setId(1);
        emptyHero.setTitle(App.getContext().getString(R.string.empty));
        emptyHero.setName(App.getContext().getString(R.string.empty));
        emptyHero.setIntroduction(App.getContext().getString(R.string.empty));
        emptyHero.setAvatarUrl(App.getContext().getString(R.string.empty));
        emptyHero.setUpGradeXP(DEF_UPGRADE_XP);
        emptyHero.setLevel(DEF_LEVEL);
    }

}
