package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class login extends AppCompatActivity {
    private Button btnlogin;
    private TextView edtUserName;
    private TextView edtPassword;
    private logindetaild titem;
    MyDBHelper DB;
    private List<String> listDetails = new ArrayList<>();
    private List<logindetaild> listDetailss = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin=findViewById(R.id.btnLogin);
        edtUserName=findViewById(R.id.edtUserName);
        edtPassword=findViewById(R.id.edtPassword);
        DB = new  MyDBHelper(this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // listDetails.add(edtPassword.getText().toString());




                String abc= edtUserName.getText().toString();
                String pqr=  edtPassword.getText().toString();

                listDetailss=DB.getdetail();

                boolean rty = DB.addContact(abc,pqr);
                if(rty==true){
                    Toast.makeText(login.this,"new entry inserted",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this,dashboard.class));
                }
                else {
                    Toast.makeText(login.this,"new entry not inserted",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}