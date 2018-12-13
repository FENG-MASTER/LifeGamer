package com.lifegamer.fengmaster.lifegamer.log.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by FengMaster on 18/12/13.
 *
 * 日志处理器函数注解
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogHandler {

    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#type
     * @return
     */
    String type();
    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#action
     * @return
     */
    String[] action();
    /**
     * @see com.lifegamer.fengmaster.lifegamer.model.Log#property
     * @return
     */
    String property();

    /**
     * 执行顺序
     * @return
     */
    String order();



}
