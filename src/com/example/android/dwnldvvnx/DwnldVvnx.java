/**
 * Octobre 2018
 * Le service le plus simple au monde! Log un truc. Le stub par excellence, interdiction de complexifier!
 * 
 * Point de départ pour tout projet...
 *  
 * Squelette (arborescence et Android.mk) tiré de development/samples/
 * make StartVvnx (LOCAL_PACKAGE_NAME dans le Android.mk
 * 
 * adb uninstall com.example.android.dwnldvvnx
 * 
 * 
 * adb install out/target/product/mido/system/app/DwnldVvnx/DwnldVvnx.apk
 * ou
 * adb install out/target/product/generic_arm64/system/app/DwnldVvnx/DwnldVvnx.apk
 * 
 * Lancement du service en shell (nom du service: celui déclaré dans le manifest -component name-) 
 * 
 * #indispensable, survit au reboot (tant que tu réinstalles pas l'appli), sinon app is in background uid null
 * dumpsys deviceidle whitelist +com.example.android.dwnldvvnx
 * 
 * am start-service com.example.android.dwnldvvnx/.DwnldVvnx  
 *  
 * 
 * logcat -s DwnldVvnx
 * 
 * 
 * Lancement avec un intent explicite, syntaxe:
 * am start-service -a android.intent.action.DIAL com.example.android.dwnldvvnx/.DwnldVvnx
 *
 * 
 * 
 */

package com.example.android.dwnldvvnx;

import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import android.content.Intent;

//Essai downloadManager
import android.app.DownloadManager;
import android.net.Uri;



public class DwnldVvnx extends Service {
	
	private static final String TAG = "DwnldVvnx";
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate");		
				
        //stopSelf(); //j'avais mis ça juste parce que le dev guide disait qu'il fallait faire le ménage soi-même
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "OnStartCommand");
		
		
		
		
		//Essai downloadManager
		DownloadManager downloadmanager = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);
		Uri uri = Uri.parse("http://www.vincentachard.fr/airmada.pdf");
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setTitle("My File");
		request.setDescription("Downloading");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(false);
		request.setDestinationUri(Uri.parse("file:///sdcard/vvnx_files/airmada.pdf"));
		downloadmanager.enqueue(request);
		
		
		
		
		
		
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

