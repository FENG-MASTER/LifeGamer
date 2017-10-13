package com.lifegamer.fengmaster.lifegamer.adapter.base;

import android.support.v7.widget.RecyclerView;

import com.lifegamer.fengmaster.lifegamer.base.IName;
import com.lifegamer.fengmaster.lifegamer.wight.SelectDialog;

/**
 * Created by qianzise on 2017/10/10.
 *
 *  基础RecyclerView适配器抽象类
 *
 *  因为我打算所有的显示内容全部交由适配器做,这里需要显示一个标题,所以必须实现{@link IName}接口
 */

public abstract class BaseRecyclerViewAdapter<T extends RecyclerView.ViewHolder,R> extends RecyclerView.Adapter<T> implements IName,ItemSelectObservable<R> {



}
