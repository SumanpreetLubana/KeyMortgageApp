package com.example.smartserve.keymortgageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.smartserve.keymortgageapp.Models.NotificationModel;

public class NewsFeedDetailsActivity extends AppCompatActivity {
ImageView imageView;
TextView textView,message;
NotificationModel notificationModel;
    ImageView back,close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_details);

        imageView =(ImageView)findViewById(R.id.imageView);
        textView =(TextView)findViewById(R.id.title);
        message =(TextView)findViewById(R.id.message);
        close =(ImageView)findViewById(R.id.close);
        back =(ImageView)findViewById(R.id.back);


        Intent intent =getIntent();
        notificationModel=intent.getParcelableExtra("notificationModel");
        Glide.with(this)
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.property_default)
                        .error(R.drawable.property_default))
                .load(notificationModel.getImage())
                .into(imageView);
        textView.setText(notificationModel.getText());
        message.setText(notificationModel.getMessage());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
