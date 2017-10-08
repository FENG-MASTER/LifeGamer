package com.lifegamer.fengmaster.lifegamer.dao.itf;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qianzise on 2017/10/6.
 *
 * 更新SQL接口
 */

public interface Updateable {

    int update(SQLiteDatabase sqLiteDatabase);
}
