package com.example.android.dwnldvvnx;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * sqlite3 /data/data/com.example.android.dwnldvvnx/databases/dwnld.db "select ID, datetime(TIME/1000, 'unixepoch', 'localtime'), DELAY FROM dwnl;"
 * 
 * 
 * récup bdd sqlite sur tel rooté et sur tel production build
 * adb pull /data/data/com.example.android.dwnldvvnx/databases/dwnld.db
 * récup bdd sqlite sur tel production build
 * adb shell 'run-as com.example.android.receivervvnx cat /data/data/com.example.android.dwnldvvnx/databases/dwnld.db' > ma.db
 * 
 * 
 */ 



public class BaseDeDonnees extends SQLiteOpenHelper {
	
	private static final String TAG = "DwnldVvnx";

    private static final String DATABASE_NAME = "dwnld.db";
    private static final int DATABASE_VERSION = 1;
    //private static final String CREATE_BDD = "CREATE TABLE loc (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER NOT NULL, CELLID INTEGER NOT NULL, MCC INTEGER NOT NULL, MNC INTEGER NOT NULL, LAC INTEGER NOT NULL, RADIO TEXT NOT NULL)";
    private static final String CREATE_BDD = "CREATE TABLE dwnl (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER NOT NULL, DELAY INTEGER NOT NULL)";

    public BaseDeDonnees(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate sql");	
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
