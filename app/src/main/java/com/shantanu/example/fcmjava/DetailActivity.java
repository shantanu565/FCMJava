package com.shantanu.example.fcmjava;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shantanu.example.fcmjava.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {
    Noti noti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        noti= ViewModelProviders.of(this).get(Noti.class);
        ActivityDetailBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        binding.setNoti(noti);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            noti.setUrl(bundle.getString("imgUrl"));
            noti.setPrice(bundle.getString("price"));
        }


    }
}
