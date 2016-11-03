package com.example.administrator.newsdemo.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/11/2.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final String TABLE_NAME1 = "news_list";
    public static final String COLUMN_NETEASE = "NetEase";
    public static final String COLUMN_NEWSCONTENT = "NewsContent";
    public static final String COLUMN_ID = "_id";

    public DBHelper(Context context) {
        super(context, "favor.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME1 + " ("+COLUMN_ID+" INTEGER primary key autoincrement not null," + COLUMN_NETEASE + " text not null," + COLUMN_NEWSCONTENT + " text not null)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "drop table if exists "+TABLE_NAME1;
        db.execSQL(sql);
        onCreate(db);
    }
}
