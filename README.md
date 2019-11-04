# Connections http URL en alrm 

 Request POST via httpurl connection et téléchargement via downloadmanager

 Initialement: téléchargement de fichier, en alarm, pour déterminer vitesse de téléchargement (Palavas Opérateurs qd plus de box...)
 
 Depuis septembre 2019: récup de la dernière location et envoi en requête http POST
  
 Squelette (arborescence et Android.mk) tiré de development/samples/ -> stub service
 ## make UrlAlrmVvnx (LOCAL_PACKAGE_NAME dans le Android.mk)
 
 ## adb uninstall com.example.android.urlalrmvvnx
 
 
 ##adb install out/target/product/mido/system/app/UrlAlrmVvnx/UrlAlrmVvnx.apk
 ou
 adb install out/target/product/generic_arm64/system/app/UrlAlrmVvnx/UrlAlrmVvnx.apk
 
 Lancement du service en shell (nom du service: celui déclaré dans le manifest -component name-) 
 
 indispensable, survit au reboot (tant que tu réinstalles pas l'appli), sinon app is in background uid null
 dumpsys deviceidle whitelist +com.example.android.urlalrmvvnx
 
 pm grant com.example.android.urlalrmvvnx android.permission.ACCESS_FINE_LOCATION
 
 # am start-service com.example.android.urlalrmvvnx/.UrlVvnx 
 
 dumpsys deviceidle whitelist +com.example.android.urlalrmvvnx; am start-service com.example.android.urlalrmvvnx/.UrlVvnx
 
 logcat -s UrlVvnx
 
 sqlite3 /data/data/com.example.android.urlalrmvvnx/databases/dwnld.db "select ID, datetime(TIME/1000, 'unixepoch', 'localtime'), DELAY FROM dwnl;"
  
 
 Lancement avec un intent explicite, syntaxe:
 am start-service -a android.intent.action.DIAL com.example.android.urlalrmvvnx/.UrlVvnx

 
 


