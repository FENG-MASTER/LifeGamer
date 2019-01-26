package com.lifegamer.fengmaster.lifegamer.event.trigger;

import java.util.Date;

/**
 * 每分钟事件
 */
public class MinuteEvent {

    private Date date;

    public MinuteEvent(Date date) {
        this.date = date;
    }


    public Date getDate() {
        return date;
    }
}
