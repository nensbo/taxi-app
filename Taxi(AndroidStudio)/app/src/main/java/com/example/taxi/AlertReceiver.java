package com.example.taxi;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.example.taxi.activity.OcenaActivity;

public class AlertReceiver /* extends BroadcastReceiver */ {

  /*  @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        int voznjaId= bundle.getInt("voznjaId");

        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannelNotification(voznjaId);

        notificationHelper.getManager().notify(1,nb.build());
    }   */
}
