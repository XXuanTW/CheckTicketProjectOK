package com.example.user.checkticketproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLdata extends SQLiteOpenHelper {

    private final static String DB = "urldata.db";
    private final static String TB = "data";
    private final static int vs = 1;

    public SQLdata(Context context) {
        super(context, DB, null, vs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = "CREATE TABLE IF NOT EXISTS " + TB
                     + "(_id INTEGER PRIMARY KEY AUTOINCREMENT , _url VARCHAR(255))";
        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL = "DROP TABLE " + TB;
        db.execSQL(SQL);
        onCreate(db);
    }
}
