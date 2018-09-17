package com.lifegamer.fengmaster.lifegamer.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.base.ICopy;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Deleteable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Insertable;
import com.lifegamer.fengmaster.lifegamer.dao.itf.Updateable;

/**
 * Created by qianzise on 2017/10/9.
 *
 * LP点数对象,LP点数都存储在这个对象里,目前只有单一对象
 */

public class LifePoint extends BaseObservable implements Updateable {

    private int _id=1;

    private int lpPoint;
    @Bindable
    public int getLpPoint() {
        return lpPoint;
    }

    public void setLpPoint(int lpPoint) {
        this.lpPoint = lpPoint;
        Game.update(this);
        notifyPropertyChanged(BR.lpPoint);
    }

    public void addPoint(int point){
        this.lpPoint +=point;
        Game.update(this);
        notifyPropertyChanged(BR.lpPoint);
    }


    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("_id", 1);
        cv.put("lifePoint",getLpPoint());
        return sqLiteDatabase.update(DBHelper.TABLE_HERO,cv,"_id =?",new String[]{String.valueOf(_id)});
    }
}
