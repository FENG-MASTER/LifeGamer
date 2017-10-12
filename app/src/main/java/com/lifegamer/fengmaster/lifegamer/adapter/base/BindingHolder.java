package com.lifegamer.fengmaster.lifegamer.adapter.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by qianzise on 2017/10/11.
 */

public class BindingHolder extends RecyclerView.ViewHolder {
    protected ViewDataBinding binding;

    public BindingHolder(View itemView) {
        super(itemView);
        binding= DataBindingUtil.bind(itemView);
    }

    public BindingHolder setBinding(int resID,Object object){
        binding.setVariable(resID,object);
        binding.executePendingBindings();
        return this;
    }

}
