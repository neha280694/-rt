package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    private Button btnlogin;
    private TextView edtUserName;
    private TextView edtPassword;
    MyDBHelper DB;
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
                String nametext= edtUserName.getText().toString();
                String passwordd = edtPassword.getText().toString();
                Boolean checkinsertdata = DB.addContact(nametext,passwordd);
                if(checkinsertdata==true){
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