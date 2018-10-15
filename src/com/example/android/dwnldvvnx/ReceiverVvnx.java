//Register du receiver DOWNLOAD_COMPLETE -> dans le manifest only, si je register dans le code aussi, tu te frappes deux onReceive() à chaque fois!


package com.example.android.dwnldvvnx;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.content.ContentResolver;
import android.content.ContentValues;


public class ReceiverVvnx extends BroadcastReceiver {
    private static final String TAG = "DwnldVvnx";

    @Override
    public void onReceive(Context context, Intent intent) {

		long timestamp = System.currentTimeMillis();
        long delay = timestamp - AlarmDwnldVvnx.launchTimestamp;
		
        Log.d(TAG, "onReceive receiver download delay = " + delay);   
		
		
		/*
		 * SQL
		 * adb pull /data/data/com.example.android.receivervvnx/databases/log_intents.db
		 *  
		 */ 
		
		ContentValues values = new ContentValues();			
		
		values.put("TIME", timestamp);
		values.put("DELAY", delay);			
		
		
		try
		{
		BaseDeDonnees maBDD = new BaseDeDonnees(context); //pas suffisant pour passer dans le onCreate() de BaseDeDonnees
		SQLiteDatabase bdd=maBDD.getWritableDatabase(); //avec ça on passe dans le onCreate()
		bdd.insert("dwnl", null, values);
		} 
		catch(SQLiteCantOpenDatabaseException e) //j'ai eu des arrêts avec android.database.sqlite.SQLiteCantOpenDatabaseException: unable to open database file (code 14)
		{
		Log.d(TAG, "erreur SQLiteCantOpenDatabaseException");	 
		}
	
		}	
		
    }

