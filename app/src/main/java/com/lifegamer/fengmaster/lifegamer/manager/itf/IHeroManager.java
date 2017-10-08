package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.model.Hero;

/**
 * Created by qianzise on 2017/10/8.
 *
 * 英雄管理器 [暂时不支持新增英雄]
 *
 */

public interface IHeroManager {

    Hero getHero();

    Hero getHero(int id);

    boolean updateHero(Hero hero);

}
