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
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MyService extends FirebaseMessagingService {
    private static final String TAG ="FirebaseMessageService";
    Bitmap bitmap;
    private NotificationCompat.Builder builder;
    private PendingIntent pendingIntent;


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        final String message = remoteMessage.getData().get("message");
        final String imageUri = remoteMessage.getData().get("image");

        RequestBuilder<Bitmap> requestBuilder = Glide.with(getApplicationContext())
                .asBitmap()
                .load(imageUri);
        requestBuilder.addListener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                func(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), resource, message, imageUri);

                return isFirstResource;
            }
        });


    }
    //here

    //here
    public void func(String title,String body,Bitmap bitmap,String msg,String url){
        Intent i=new Intent(getApplicationContext(),DetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("imageUri",url);
        bundle.putString("message",msg);
        i.putExtras(bundle);
        pendingIntent=PendingIntent.getActivity(getApplicationContext(),1,i,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(msg)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLargeIcon(bitmap)
                .setChannelId("com.shantanu.example.fcmjava.stopwatchID")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(101, notificationBuilder.build());


    }


}

