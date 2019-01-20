package com.lifegamer.fengmaster.lifegamer.util;

import com.lifegamer.fengmaster.lifegamer.model.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by qianzise on 2017/10/26.
 * <p>
 * 时间相关工具类
 */

public class DateUtil {

    /**
     * 获取今天24点时间
     *
     * @return 时间
     */
    public static Calendar getEndOfToday() {
        Calendar calendar = Calendar.getInstance();
        //天数+1
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        //0点
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //0分
        calendar.set(Calendar.MINUTE, 0);
        //0秒
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    /**
     * 获取今天0点时间
     *
     * @return 时间
     */
    public static Calendar getStartOfToday() {
        Calendar calendar = Calendar.getInstance();
        //0点
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //0分
        calendar.set(Calendar.MINUTE, 0);
        //0秒
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    /**
     * 获取明天24点时间
     *
     * @return 时间
     */
    public static Calendar getEndOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        //天数+2
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
        //0点
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //0分
        calendar.set(Calendar.MINUTE, 0);
        //0秒
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    /**
     * 获取两个日期时间的间隔,当1>2的时候,返回正数,否则相反
     *
     * @param date1 时间1
     * @param date2 时间2
     * @param type  返回时间类型 {@link Calendar}
     * @return 间隔时间 返回-1表示返回类型不支持
     */
    public static int getDateDistance(Date date1, Date date2, int type) {
        switch (type) {
            case Calendar.HOUR:
                return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60));
            case Calendar.MINUTE:
                return (int) ((date1.getTime() - date2.getTime()) / (1000 * 60));
            default:
                return -1;
        }

    }

    /**
     * 返回当前时间
     * @return 当前时间
     */
    public static Date getNowDate(){
        return new Date();
    }

}
