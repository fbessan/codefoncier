package com.besco.innova.codefoncier.FCM;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.besco.innova.codefoncier.ConseilActivity;
import com.besco.innova.codefoncier.MainActivity;
import com.besco.innova.codefoncier.R;
import com.besco.innova.codefoncier.models.Conseil;
import com.besco.innova.codefoncier.models.Faq;
import com.besco.innova.codefoncier.models.Titre;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;


/**
 * Created by fbessan on 04/10/2016.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private FragmentManager myContext;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        createNotification(remoteMessage.getData().get("message"),remoteMessage.getData().get("nowdate"));

        Log.e("LOGFCM"," VALLENGHT "+remoteMessage.getData().get("message").length());
        Log.e("LOGFCM"," VALNOW "+remoteMessage.getData().get("nowdate"));
        Log.e("LOGFCM"," VALMSG "+remoteMessage.getData().get("message"));

    }



    private void createNotification( String messageBody,String nowdateBody) {

        if(messageBody.length() > 0 && nowdateBody.length() > 0){

            //Save in db

            List<Conseil> conseils = Conseil.findWithQuery(Conseil.class, "SELECT * FROM Conseil", null);
            Log.e("LOGFCM"," VALMSGMESSAGEPUSH "+conseils.size());
            int idConseil = conseils.size() + 1;
            Conseil conseil = new Conseil(idConseil,nowdateBody,messageBody);
            conseil.save();

            //Send Notification
            Intent intent = new Intent( this , ConseilActivity.class );

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                    .setSmallIcon(R.drawable.ic_stat_benin)
                    .setContentTitle("Conseil du jour")
                    .setContentText(messageBody)
                    .setAutoCancel( true )
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, mNotificationBuilder.build());
        }



    }




}
