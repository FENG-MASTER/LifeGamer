package com.lifegamer.fengmaster.lifegamer.adapter.base;

/**
 * Created by qianzise on 2017/10/13.
 *
 * item选中事件源接口
 */

public interface ItemSelectObservable<T> {


    void addItemSelectListener(OnItemSelectListener<T> listener);

    void removeItemSelectListener(OnItemSelectListener<T> listener);
}
