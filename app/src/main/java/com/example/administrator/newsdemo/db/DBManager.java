package com.example.administrator.newsdemo.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;


import com.example.administrator.newsdemo.entity.NetEase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */

public class DBManager {
    private static DBManager sDBManager;
    private final DBHelper helper;

    public static DBManager getDBManager(Context context) {
        if (sDBManager == null) {
            sDBManager = new DBManager(context);
        }
        return sDBManager;
    }

    private DBManager(Context context) {
        helper = new DBHelper(context);
        helper.getReadableDatabase();
    }

    public long insertNewsData(NetEase netEase, String parsedContent) {
        if (query(netEase)) {
            return -1;
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put(DBHelper.COLUMN_NETEASE, gson.toJson(netEase));
        values.put(DBHelper.COLUMN_NEWSCONTENT, parsedContent);
        long i = db.insert(DBHelper.TABLE_NAME1, null, values);
        db.close();
        return i;
    }

    public boolean query(NetEase netEase) {
        SQLiteDatabase db = helper.getReadableDatabase();
        boolean isExist = false;
        Cursor c = db.rawQuery("select " + DBHelper.COLUMN_NETEASE + " from " + DBHelper.TABLE_NAME1, null);
        while (c.moveToNext()) {
            String str = c.getString(c.getColumnIndex(DBHelper.COLUMN_NETEASE));
            Gson gson = new Gson();
            NetEase netEase1 = gson.fromJson(str, NetEase.class);
            if (netEase.docid.equals(netEase1.docid)) {
                isExist = true;
                break;
            }
        }
        db.close();
        return isExist;
    }

    public List<NetEase> queryAllList() {
        ArrayList<NetEase> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select " + DBHelper.COLUMN_NETEASE + " from " + DBHelper.TABLE_NAME1, null);
        while (c.moveToNext()) {
            String str = c.getString(c.getColumnIndex(DBHelper.COLUMN_NETEASE));
            Gson gson = new Gson();
            NetEase netEase1 = gson.fromJson(str, NetEase.class);
            list.add(netEase1);
        }
        return list;
    }

    /**
     * 通过新闻的docid来删除新闻
     *
     * @param docId
     */
    public int removeNewsById(@NonNull String docId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select " + DBHelper.COLUMN_NETEASE + " from " + DBHelper.TABLE_NAME1, null);
        String str = null;
        while (c.moveToNext()) {
            Gson gson = new Gson();
            str = c.getString(c.getColumnIndex(DBHelper.COLUMN_NETEASE));
            NetEase netEase1 = gson.fromJson(str, NetEase.class);
            if (docId.equals(netEase1.docid)) {
                str = c.getString(c.getColumnIndex(DBHelper.COLUMN_NETEASE));
                break;
            }
        }
        int result = -1;
        result = db.delete(DBHelper.TABLE_NAME1, DBHelper.COLUMN_NETEASE + "=?", new String[]{str});
        db.close();
        return result;
    }
}
