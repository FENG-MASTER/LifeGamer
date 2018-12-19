package com.lifegamer.fengmaster.lifegamer.base;

/**
 * Created by FengMaster on 18/12/19.
 * 包装类,用于包装两个对象为一个对象
 */
public class Entry<T,R> {
    private T t;

    private R r;

    public Entry(T t, R r) {
        this.t = t;
        this.r = r;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public R getR() {
        return r;
    }

    public void setR(R r) {
        this.r = r;
    }
}
