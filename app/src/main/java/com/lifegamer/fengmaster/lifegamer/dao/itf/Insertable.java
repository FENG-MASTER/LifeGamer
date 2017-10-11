package com.lifegamer.fengmaster.lifegamer.dao.itf;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by qianzise on 2017/10/6.
 *
 * 插入SQL接口
 */

public interface Insertable {

    /**
     * 插入
     * @param sqLiteDatabase 可写数据库
     * @return 插入后的ID
     */
    long insert(SQLiteDatabase sqLiteDatabase);

}
