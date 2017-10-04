package com.lifegamer.fengmaster.lifegamer.manager.itf;

import com.lifegamer.fengmaster.lifegamer.event.wealth.LPChangeEvent;

/**
 * Created by qianzise on 2017/10/4.
 *
 * 英雄财富管理接口
 *
 * LP=LifePoint
 *
 */

public interface IWealthManager {

    /**
     * 获得当前LP数
     * @return LP
     */
    int getLP();

    /**
     * 增加LP
     *
     * 函数有义务发送LP改变事件{@link LPChangeEvent}
     *
     * @param lifePoint 增加的LP
     */
    void addLP(int lifePoint);

}
