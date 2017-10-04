package com.lifegamer.fengmaster.lifegamer.event.wealth;

/**
 * Created by qianzise on 2017/10/4.
 *
 * LP变化事件
 */

public class LPChangeEvent {

    private int LP;

    public LPChangeEvent(int LP) {
        this.LP = LP;
    }

    public int getLP() {
        return LP;
    }
}
