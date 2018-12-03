package com.lifegamer.fengmaster.lifegamer.dao.itf;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 可从数据库中获取的 对象 接口,用于从数据库更新对象
 */
public interface Getable {

    void getFromDb(SQLiteDatabase sqLiteDatabase);

    void getFromCursor(Cursor cursor);

}
