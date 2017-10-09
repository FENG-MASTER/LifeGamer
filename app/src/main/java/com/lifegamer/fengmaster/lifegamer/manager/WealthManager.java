package com.lifegamer.fengmaster.lifegamer.manager;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IWealthManager;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;

/**
 * Created by qianzise on 2017/10/4.
 */

public class WealthManager implements IWealthManager {


    @Override
    public int getLP() {
        return 0;
    }

    @Override
    public void addLP(int lifePoint) {

    }

    @Override
    public LifePoint getLPInstance() {
        return null;
    }


}
