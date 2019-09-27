

package com.example.android.urlalrmvvnx;

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

import android.os.Environment;
import java.io.File;

import android.os.AsyncTask;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import org.json.JSONObject;





public class AlarmUrlVvnx extends Service {
	
	private static final String TAG = "UrlVvnx";
	
	public static long launchTimestamp;
 
    @Override
    public void onCreate() {
		Log.d(TAG, "onCreate dans AlarmUrlVvnx");			
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		
		Log.d(TAG, "OnStartCommand dans AlarmUrlVvnx");	
		
		//POST Request, déporté dans AsyncTask sinon erreuur runtime android.os.NetworkOnMainThreadException
		new PostRequestTask().execute();		
		
		//ma fonction download
		//downLoad();
		
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
	
	private void downLoad() {
		Log.d(TAG, "Début download");
		//Téléchargement via DownloadManager
		DownloadManager downloadmanager = (DownloadManager) getSystemService(this.DOWNLOAD_SERVICE);		
		Uri uri = Uri.parse("http://5.135.183.126:8778/test.img");		
		/**
		 * Si tu veux accès au dir: downloadManager n'overwrite pas, tu te retrouves avec des fichier-N.ext
		 * ce bout de code vide le dir. Il est fait pour aller avec:
		 * request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "test.img");
		File dir_dest = new File("/storage/emulated/0/Android/data/com.example.android.dwnldvvnx/files/Download"); 		
		if(dir_dest.isDirectory())
		{
		for (File child : dir_dest.listFiles())
            child.delete();
		}*/		
		//Détails request sur https://developer.android.com/reference/android/app/DownloadManager.Request
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setTitle("UrlDwnldVvnx");
		request.setDescription("Downloading");		
		//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //bruit + notif barre
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN); //nécessite permission DOWNLOAD_WITHOUT_NOTIFICATION
		request.setVisibleInDownloadsUi(false);			
		//Si je mets aucune destination: fichier doit être en cache qq part et je le vois jamais! (pratique pour tests) 
		//à décommenter si tu veux avoir accès au fichier (i.e. animation satellite)
		//request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "test.img"); //  --> /storage/emulated/0/Android/data/com.example.android.dwnldvvnx/files/Download/
		//Nécessite	 permission.WRITE_EXTERNAL_STORAGE (manifest + param!!!), je laisse pour info, chiant à utiliser...
		//request.setDestinationUri(Uri.parse("file:///sdcard/vvnx_files/airmada.pdf")); //il lui faut
		downloadmanager.enqueue(request);		
		//Register du receiver DOWNLOAD_COMPLETE -> dans le manifest only, si je le mets ici aussi, tu te frappes deux onReceive() à chaque fois!
		launchTimestamp = System.currentTimeMillis();
	}
	
	
	
	private class PostRequestTask extends AsyncTask<Void,Void,Void> {
		//https://alvinalexander.com/android/asynctask-examples-parameters-callbacks-executing-canceling
    
	    @Override
	    protected Void doInBackground(Void... params) {
			String error_code = "HTTP_REPLY_NON_INITIALISEE";
			
			try {	
			URL url = new URL("http://5.135.183.126:8050/essai_post");
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type","application/json");
			urlConnection.setRequestProperty("charset", "utf-8");
			DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());
			//out.write(jsonEnvoi.toString().getBytes("iso-8859-15"));
			String mon_json = "{\"username\":\"mido\"}";
			out.writeBytes(mon_json);
			out.flush();
			out.close();
			error_code = urlConnection.getResponseMessage();
			Log.d(TAG, "Vincent reponse getResponseMessage= " + error_code);	
	
	        } catch (MalformedURLException me) {
	            Log.d(TAG, "MalformedURLException: " + me);
	
	        } catch (IOException ioe) {
	            Log.d(TAG, "IOException: " + ioe);
	        }
			return null;
	    }

	}
	
	
	
}



