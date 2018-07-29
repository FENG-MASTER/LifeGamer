package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IHeroManager;
import com.lifegamer.fengmaster.lifegamer.model.Hero;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;

import static com.lifegamer.fengmaster.lifegamer.dao.DBHelper.TABLE_HERO;

/**
 * Created by qianzise on 2017/10/8.
 *
 * 本地英雄信息管理器
 */

public class HeroManger implements IHeroManager {
    private Hero hero;

    @Override
    public Hero getHero() {
        if (hero==null){
            synchronized (this){
                if (hero==null){
                    Hero h = getHeroFromSQL(1);
                    if (h==null){
                        hero=Hero.emptyHero;
                        Game.insert(hero);
                    }else {
                        hero=h;
                    }
                }
            }
        }
        return hero;
    }

    @Override
    public Hero getHero(int id) {
        //暂时只有一个hero
        return hero;
    }

    @Override
    public boolean updateHero(Hero hero) {
        if (hero.getId()==0){
            long l = Game.insert(hero);
            hero.setId((int) l);
            return l!=0;
        }
        return Game.update(hero);
    }


    /**
     * 从数据库获取hero信息
     * @param id 英雄id
     * @return hero
     */
    private Hero getHeroFromSQL(int id){

        Cursor query = DBHelper.getInstance().getReadableDatabase().query(TABLE_HERO, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (query==null||query.getCount()==0||!query.moveToNext()){
            return null;
        }
        Hero hero=new Hero();
        hero.setId(query.getLong(query.getColumnIndex("_id")));
        hero.setName(query.getString(query.getColumnIndex("name")));
        hero.setAvatarUrl(query.getString(query.getColumnIndex("avatar")));
        hero.setIntroduction(query.getString(query.getColumnIndex("introduction")));
        hero.setLevel(query.getInt(query.getColumnIndex("level")));
        hero.setTitle(query.getString(query.getColumnIndex("title")));
        hero.setUpGradeXP(query.getInt(query.getColumnIndex("upGradeXP")));
        hero.setXp(query.getInt(query.getColumnIndex("xp")));

        //先不管那个hero 的id,以后再说啦

        LifePoint lifePoint=new LifePoint();
        lifePoint.setLpPoint(query.getInt(query.getColumnIndex("lifePoint")));
        hero.setLifePoint(lifePoint);


        query.close();

        return hero;
    }
}
