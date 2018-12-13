package com.lifegamer.fengmaster.lifegamer.log.undo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 撤销注解,用于处理对应日志撤销函数
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UndoHandler {

    String action();

    String type();

    String property();

}
