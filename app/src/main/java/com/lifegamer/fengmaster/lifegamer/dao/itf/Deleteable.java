package com.lifegamer.fengmaster.lifegamer.dao.itf;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qianzise on 2017/10/6.
 *
 * 删除SQL接口
 */

public interface Deleteable {

    int delete(SQLiteDatabase sqLiteDatabase);
}
