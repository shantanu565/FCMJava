package com.shantanu.example.fcmjava;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Noti extends ViewModel {
    String price;
    String url;


    public void setPrice(String price){
        this.price=price;
    }

    public void setUrl(String msg){
        this.url=url;
    }

    public String getPrice(){
        return price;
    }
    public String getUrl(){
        return url;
    }


}
