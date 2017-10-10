package com.lifegamer.fengmaster.lifegamer.base;

/**
 * Created by qianzise on 2017/10/11.
 *
 * 从T对象复制
 *
 * 即不改变自身引用,复制T
 */

public interface ICopy<T> {

    void copyFrom(T t);

}
