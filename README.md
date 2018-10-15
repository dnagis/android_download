# Téléchargement de fichier, en alarm, pour déterminer vitesse de téléchargement


  
 Squelette (arborescence et Android.mk) tiré de development/samples/ -> stub service
 ## make DwnldVvnx (LOCAL_PACKAGE_NAME dans le Android.mk)
 
 ## adb uninstall com.example.android.dwnldvvnx
 
 
 ## adb install out/target/product/mido/system/app/DwnldVvnx/DwnldVvnx.apk
 ou
 adb install out/target/product/generic_arm64/system/app/DwnldVvnx/DwnldVvnx.apk
 
 Lancement du service en shell (nom du service: celui déclaré dans le manifest -component name-) 
 
 indispensable, survit au reboot (tant que tu réinstalles pas l'appli), sinon app is in background uid null
 dumpsys deviceidle whitelist +com.example.android.dwnldvvnx
 
 # am start-service com.example.android.dwnldvvnx/.DwnldVvnx  
 
 dumpsys deviceidle whitelist +com.example.android.dwnldvvnx; am start-service com.example.android.dwnldvvnx/.DwnldVvnx
 
 logcat -s DwnldVvnx
 
 sqlite3 /data/data/com.example.android.dwnldvvnx/databases/dwnld.db "select ID, datetime(TIME/1000, 'unixepoch', 'localtime'), DELAY FROM dwnl;"
  
 
 Lancement avec un intent explicite, syntaxe:
 am start-service -a android.intent.action.DIAL com.example.android.dwnldvvnx/.DwnldVvnx

 
 


