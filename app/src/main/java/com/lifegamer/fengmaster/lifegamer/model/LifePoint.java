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
 */

public class LifePoint extends BaseObservable implements Insertable, Updateable {

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
    public long insert(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        //TODO:由于Game对象此时为空,暂时无法使用hero控制器
        cv.put("heroID", 1);
        cv.put("lifePoint",getLpPoint());
        return sqLiteDatabase.insert(DBHelper.TABLE_WEALTH, null, cv);
    }

    @Override
    public int update(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv=new ContentValues();
        cv.put("heroID", 1);
        cv.put("lifePoint",getLpPoint());
        return sqLiteDatabase.update(DBHelper.TABLE_WEALTH,cv,"_id =?",new String[]{String.valueOf(_id)});
    }
}
