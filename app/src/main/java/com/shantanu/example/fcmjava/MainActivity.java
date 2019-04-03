package com.shantanu.example.fcmjava;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.shantanu.example.fcmjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //subscribeToPushService();


        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationChannel channelStopWatch = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channelStopWatch = new NotificationChannel("com.shantanu.example.fcmjava.stopwatchID", "com.shantanu.example.fcmjava.stopwatchName", NotificationManager.IMPORTANCE_HIGH);
            channelStopWatch.enableVibration(true);
            manager.createNotificationChannel(channelStopWatch);


        }
        binding.btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationStopwatch();
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });
    }

    public void notificationStopwatch() {
        NotificationCompat.Builder notiOne = new NotificationCompat.Builder(getApplicationContext(), "com.shantanu.example.fcmjava.stopwatchID")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Stopwatch Notice")
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentText("running");
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notiOne.setSound(alarm);

        manager.notify(1, notiOne.build());

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            public void run() {
                handler1.postDelayed(this, 60000);
            }
        }, 120000);
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
        String token = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
    }


}
