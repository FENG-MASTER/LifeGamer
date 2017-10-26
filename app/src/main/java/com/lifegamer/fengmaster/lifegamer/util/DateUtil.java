package com.lifegamer.fengmaster.lifegamer.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by qianzise on 2017/10/26.
 *
 * 时间相关工具类
 */

public class DateUtil {

    /**
     * 获取今天24点时间
     * @return 时间
     */
    public static Calendar getEndOfToday(){
        Calendar calendar=Calendar.getInstance();
        //天数+1
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+1);
        //0点
        calendar.set(Calendar.HOUR_OF_DAY,0);
        //0分
        calendar.set(Calendar.MINUTE,0);
        //0秒
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }


    /**
     * 获取今天0点时间
     * @return 时间
     */
    public static Calendar getStartOfToday(){
        Calendar calendar=Calendar.getInstance();
        //0点
        calendar.set(Calendar.HOUR_OF_DAY,0);
        //0分
        calendar.set(Calendar.MINUTE,0);
        //0秒
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }

    /**
     * 获取明天24点时间
     * @return 时间
     */
    public static Calendar getEndOfTomorrow(){
        Calendar calendar=Calendar.getInstance();
        //天数+2
        calendar.set(Calendar.DAY_OF_YEAR,calendar.get(Calendar.DAY_OF_YEAR)+1);
        //0点
        calendar.set(Calendar.HOUR_OF_DAY,0);
        //0分
        calendar.set(Calendar.MINUTE,0);
        //0秒
        calendar.set(Calendar.SECOND,0);
        return calendar;
    }

}
