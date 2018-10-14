//Register du receiver DOWNLOAD_COMPLETE -> dans le manifest only, si je régister dans le code aussi, tu te frappes deux onReceive() à chaque fois!


package com.example.android.dwnldvvnx;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ReceiverVvnx extends BroadcastReceiver {
    private static final String TAG = "DwnldVvnx";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d(TAG, "onReceive dans mon le receiver de download, action=" + intent.getAction() + AlarmDwnldVvnx.lastTimestamp);
        //long completeTimestamp = System.currentTimeMillis();
        long delay = System.currentTimeMillis() - AlarmDwnldVvnx.launchTimestamp;
		
		//Log.d(TAG, "onReceive receiver download ts_launch = "+ AlarmDwnldVvnx.launchTimestamp + " et ts fin = " + completeTimestamp);
        Log.d(TAG, "onReceive receiver download delay = " + delay);   
		}
    }

