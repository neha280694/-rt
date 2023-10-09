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
    private LinearLayout loanemi,sipcal,currency,patternlock,videoedt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        usrd=findViewById(R.id.usrd);
        weatherd=findViewById(R.id.weatherd);
        serverd=findViewById(R.id.serverd);
        loanemi=findViewById(R.id.loanemi);
        sipcal=findViewById(R.id.sipcal);
        currency=findViewById(R.id.currency);
        patternlock=findViewById(R.id.patternlock);
        videoedt=findViewById(R.id.videoedt);
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
      /* serverd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(dashboard.this,MainActivity.class));


           }
       });*/
       loanemi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(dashboard.this,emicalculator.class));
           }
       });
       sipcal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,calculatorsip.class));

           }
       });
       currency.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,currencyconverter.class));

           }
       });
       patternlock.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,lockscreenpattern.class));

           }
       });
       videoedt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(dashboard.this,videoeditor.class));

           }
       });

    }




}