package com.lifegamer.fengmaster.lifegamer.manager;

import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IHeroManager;
import com.lifegamer.fengmaster.lifegamer.model.Hero;

/**
 * Created by qianzise on 2017/10/8.
 */

public class HeroManger implements IHeroManager {
    private Hero hero;

    @Override
    public Hero getHero() {
        if (hero==null){
            synchronized (this){
                if (hero==null){
                    Hero h = DBHelper.getInstance().getHero(1);
                    if (h==null){
                        hero=Hero.emptyHero;
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
}
