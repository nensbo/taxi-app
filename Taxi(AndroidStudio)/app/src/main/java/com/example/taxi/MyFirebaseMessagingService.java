package com.example.taxi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.taxi.activity.MainActivity;
import com.example.taxi.activity.NotifikacijeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private int voznjaId;
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);


        Log.d(TAG,"token: "+token);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        Log.d(TAG, "Notifikacija" + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Notifikacija: Naslov " + title + "tekst: " + body);
            showNotification(title, body);
        }


      //  if (remoteMessage.getData().size() > 0) {

            //iterate the map to get key and value
        String value1=null,value2=null;
        if(remoteMessage.getData().size()> 0 ){
            for(Map.Entry<String,String> entry : remoteMessage.getData().entrySet()){
                if(entry.getKey().equals("myKey1"))value1=entry.getValue();
                if(entry.getKey().equals("myKey2"))value2=entry.getValue();

                Log.d(TAG,"Key: "+entry.getKey()+" - Value: "+entry.getValue());}

        if(value1.equals("prihvacena")){
            voznjaId=Integer.valueOf(value2)  ;}}
        else{
            Map <String, String> entry=remoteMessage.getData();
            value1=entry.get("myKey1");

        }

        //show notification
            //showNotification(title, body);

       // for(Map <String, String> entry : remoteMessage.getData())


        //     String value2=entry.get("myKey2");
//                Log.d(TAG, value1);
        //        Log.d(TAG, "Key: " + entry.getKey() + " - Value: " + entry.getValue());
         //   }
            String naslov=null,telo=null;

            //show notification if required
        switch(value1){
            case ("0"):
                naslov="Uspešno kreirana vožnja";
                telo="Uspešno ste kreirali vožnju, uskoro će Vam se javiti vozač!";
            break;
            case("prihvacena"):
                naslov="Vožnja je prihvaćena";
                telo="Dodatne informacije o vožnji:";
                break;
            case("aktivna"):
                naslov="Vozilo je na Vašoj adresi";
                telo="Želimo Vam ugodnu vožnju";
                break;
            case("zavrsena"):
                naslov="Vožnja je završena!";
                telo="Radi poboljšanja kvaliteta usluge, ocenite vožnju!";
                break;
            case("otkazana"):
                naslov="Vožnja je otkazana";
                telo="Uskoro će vožnju preuzeti drugi vozač!";
                break;
        }
            showNotification(naslov, telo);
       // }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //channel_id should be unique for every notification channel
        NotificationChannel notificationChannel = new NotificationChannel("channel_id", "Test Notification Channel",
                NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("My test notification channel");

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, NotifikacijeActivity.class);
        intent.putExtra("naslov",title);
        intent.putExtra("telo",body);
        if(voznjaId!=0){
            intent.putExtra("voznjaId", voznjaId);
            Log.i("voznjaID","voznjaId "+voznjaId);

        }


        //pass the same channel_id which we created in previous method
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.taxi)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        notificationManager.notify(1, builder.build());
    }
}
