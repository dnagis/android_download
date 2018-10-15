

package com.example.android.dwnldvvnx;

import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import android.content.Intent;
//DownloadManager
import android.app.DownloadManager;
import android.net.Uri;
//Broadcast Receiver
import android.content.BroadcastReceiver;
import android.content.IntentFilter;





public class AlarmDwnldVvnx extends Service {
	
	private static final String TAG = "DwnldVvnx";
	
	public static long launchTimestamp;
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate dans AlarmDwnldVvnx");			
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d(TAG, "OnStartCommand dans AlarmDwnldVvnx");		
		
		//Téléchargement via DownloadManager
		DownloadManager downloadmanager = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);
		//Uri uri = Uri.parse("http://www.vincentachard.fr/airmada.pdf");
		Uri uri = Uri.parse("http://5.135.183.126:8778/test.img");
		
		//Détails request sur https://developer.android.com/reference/android/app/DownloadManager.Request
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setTitle("DwnldVvnx");
		request.setDescription("Downloading");		
		//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //bruit + notif barre
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN); //nécessite permission DOWNLOAD_WITHOUT_NOTIFICATION
		request.setVisibleInDownloadsUi(false);		
		//request.setDestinationUri(Uri.parse("file:///sdcard/vvnx_files/airmada.pdf")); //il lui faut permission.WRITE_EXTERNAL_STORAGE (manifest + param!!!)
		downloadmanager.enqueue(request);		
		//Register du receiver DOWNLOAD_COMPLETE -> dans le manifest only, si je le mets ici aussi, tu te frappes deux onReceive() à chaque fois!
		launchTimestamp = System.currentTimeMillis();
		
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

