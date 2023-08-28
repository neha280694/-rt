package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {
  private LinearLayout usrd;
  private LinearLayout weatherd;
    private TextView serverd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        usrd=findViewById(R.id.usrd);
        weatherd=findViewById(R.id.weatherd);
        serverd=findViewById(R.id.serverd);
        usrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dashboard.this,userlist.class));

            }
        });
       weatherd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,calculator.class));


           }
       });
       serverd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,MainActivity.class));


           }
       });

    }
}