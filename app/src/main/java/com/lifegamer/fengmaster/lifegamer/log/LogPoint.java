package com.lifegamer.fengmaster.lifegamer.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by FengMaster on 18/12/06.
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
