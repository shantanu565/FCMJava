package com.shantanu.example.fcmjava;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shantanu.example.fcmjava.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    Noti noti;
    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView=findViewById(R.id.imgItem);
        textView=findViewById(R.id.tvPrice);

        noti= ViewModelProviders.of(this).get(Noti.class);
        ActivityDetailBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        binding.setNoti(noti);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            noti.setUrl(bundle.getString("imageUri"));
            noti.setPrice(bundle.getString("message"));

            textView.setText(bundle.get("message").toString());
            Glide.with(this).load(bundle.get("imageUri")).into(imageView);
        }


    }
}
