

package com.example.android.dwnldvvnx;

import android.app.Service;
import android.util.Log;
import android.os.IBinder;
import android.content.Intent;
//DownloadManager
import android.app.DownloadManager;
import android.net.Uri;



public class AlarmDwnldVvnx extends Service {
	
	private static final String TAG = "AlarmDwnldVvnx";
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate");				
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d(TAG, "OnStartCommand");		
		
		//Téléchargement via DownloadManager
		DownloadManager downloadmanager = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);
		//Uri uri = Uri.parse("http://www.vincentachard.fr/airmada.pdf");
		Uri uri = Uri.parse("http://5.135.183.126:8778/animation.gif");
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setTitle("DwnldVvnx");
		request.setDescription("Downloading");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(false);		
		//request.setDestinationUri(Uri.parse("file:///sdcard/vvnx_files/airmada.pdf")); //il lui faut permission.WRITE_EXTERNAL_STORAGE (manifest + param!!!)
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

