package com.lifegamer.fengmaster.lifegamer.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by FengMaster on 18/12/06.
 *
 * 日志注解,用于需要写日志的函数
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPoint {


    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#type
     * @return
     */
    String type();
    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#action
     * @return
     */
    String action();
    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#property
     * @return
     */
    String property();

}
