package com.lifegamer.fengmaster.lifegamer.manager;

import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.lifegamer.fengmaster.lifegamer.BR;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.dao.DBHelper;
import com.lifegamer.fengmaster.lifegamer.manager.itf.IWealthManager;
import com.lifegamer.fengmaster.lifegamer.model.LifePoint;

/**
 * Created by qianzise on 2017/10/4.
 */

public class WealthManager implements IWealthManager {

    private DBHelper helper=DBHelper.getInstance();

    private LifePoint lifePoint;

    public WealthManager() {
        loadFromSQL();
    }

    @Override
    public int getLP() {
        return lifePoint.getLpPoint();
    }

    @Override
    public void addLP(int p) {
        lifePoint.addPoint(p);
    }

    @Override
    public LifePoint getLPInstance() {
        return lifePoint;
    }

    private void loadFromSQL(){

        //先不管那个hero 的id,以后再说啦
        Cursor cursor = helper.getReadableDatabase().query(DBHelper.TABLE_WEALTH, null, null, null, null, null, null);
        if (cursor.moveToNext()){
            lifePoint=new LifePoint();
            lifePoint.setLpPoint(cursor.getInt(cursor.getColumnIndex("lifePoint")));
        }else {
            lifePoint=new LifePoint();
            Game.insert(lifePoint);
        }

        cursor.close();

    }


}
