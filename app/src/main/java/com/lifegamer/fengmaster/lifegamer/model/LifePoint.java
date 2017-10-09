package com.lifegamer.fengmaster.lifegamer.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;

/**
 * Created by qianzise on 2017/10/9.
 */

public class LifePoint extends BaseObservable{

    private int lpPoint;
    @Bindable
    public int getLpPoint() {
        return lpPoint;
    }

    public void setLpPoint(int lpPoint) {
        this.lpPoint = lpPoint;
        notifyPropertyChanged(BR.lpPoint);
    }

    public void addPoint(int point){
        this.lpPoint +=point;
        notifyPropertyChanged(BR.lpPoint);
    }
}
