/**
 * Octobre 2018
 * ***Téléchargement automatique sous Alarm, via DownloadManager. L'appel de ce service crée une alarm
 * qui déclenche le service à côté: AlarmUrlVvnx***
 * 
 * Le LOG que j'ai rédigé:  kimsufi @ /home/android/LOG_download
 * 
 * #compilation
 * make UrlAlrmVvnx (LOCAL_PACKAGE_NAME dans le Android.mk)
 * 
 * adb uninstall com.example.android.urlalrmvvnx
 * 
 * adb install out/target/product/mido/system/app/UrlAlrmVvnx/UrlAlrmVvnx.apk
 * ou
 * adb install out/target/product/generic_arm64/system/app/UrlAlrmVvnx/UrlAlrmVvnx.apk
 * 
 * Lancement du service en shell (nom du service: celui déclaré dans le manifest -component name-) 
 * 
 * #indispensable avant lancement, survit au reboot (tant que tu réinstalles pas l'appli), sinon erreur: app is in background uid null
 * dumpsys deviceidle whitelist +com.example.android.urlalrmvvnx
 * 
 * pm grant com.example.android.urlalrmvvnx android.permission.ACCESS_FINE_LOCATION
 * 
 * am start-service com.example.android.urlalrmvvnx/.UrlVvnx
 * 
 * dumpsys deviceidle whitelist +com.example.android.urlalrmvvnx; am start-service com.example.android.urlalrmvvnx/.UrlVvnx
 * 
 * logcat -s UrlVvnx
 * ou
 * logcat -s AlarmDwnldVvnx
 * 
 * am force-stop com.example.android.urlalrmvvnx
 * 
 * 
 * Lancement avec un intent explicite, syntaxe:
 * am start-service -a android.intent.action.DIAL com.example.android.urlalrmvvnx/.UrlVvnx
 * 
 * 
 *
 * sqlite3 /data/data/com.example.android.urlalrmvvnx/databases/dwnld.db "select ID, datetime(TIME/1000, 'unixepoch', 'localtime'), DELAY FROM dwnl;"
 * 
 */

package com.example.android.urlalrmvvnx;

import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import android.content.Intent;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.SystemClock;




public class UrlVvnx extends Service {
	
	private static final String TAG = "UrlVvnx";
	
	// 80 * 1000 = 80 seconds in milliseconds 
    //de toutes façons en dessous de 60s: W AlarmManager: Suspiciously short interval 30000 millis; expanding to 60 seconds

    private static final long PERIODE_MS = 300 * 1000;

    // An intent for AlarmService, to trigger it as if this service called startService().
    private PendingIntent mAlarmSender;

    // Contains a handle to the system alarm service
    private AlarmManager mAlarmManager;
 
 
 
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate dans UrlVvnx");		

        // Create a PendingIntent to trigger a startService() for AlarmDwnldVvnx
        mAlarmSender = PendingIntent.getService(  // set up an intent for a call to a service (voir dev guide intents à "Using a pending intent")
            this,  // the current context
            0,  // request code (not used)
            new Intent (this, AlarmUrlVvnx.class),  // A new Service intent 'c'est un intent explicite'
            0   // flags (none are required for a service)
        );

        // Gets the handle to the system alarm service
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        
        long firstAlarmTime = SystemClock.elapsedRealtime();
        
        mAlarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP, // based on time since last wake up
                firstAlarmTime,  // sends the first alarm immediately
                PERIODE_MS,  // repeats every XX
                mAlarmSender  // when the alarm goes off, sends this Intent
            );
            
 
            
               
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "OnStartCommand dans UrlVvnx");	
		return START_NOT_STICKY;
	}

    @Override
    public void onDestroy() {		
		Log.d(TAG, "OnDestroy");
		stopSelf();		
	 }
	 
	  @Override
	public IBinder onBind(Intent intent) {
      // We don't provide binding, so return null
      return null;
	}
}

