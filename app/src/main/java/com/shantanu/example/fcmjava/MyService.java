package com.shantanu.example.fcmjava;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        String message = remoteMessage.getData().get("message");
        String imageUri = remoteMessage.getData().get("image");
        Log.d("msg",message+imageUri);
        Intent i=new Intent(getApplicationContext(),DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("imageUri",imageUri);
        bundle.putString("message",message);
        i.putExtras(bundle);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        NotificationCompat.BigPictureStyle bigPictureStyle =new NotificationCompat.BigPictureStyle().bigPicture(resource);
                        bigPictureStyle.setSummaryText("Image Description");
                        notificationBuilder.setStyle(bigPictureStyle);
                        //NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        //notificationManager.notify(0, notificationBuilder.build());
                        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
                        notificationManagerCompat.notify(11,notificationBuilder.build());

                    }
                });

        //
    }


}
