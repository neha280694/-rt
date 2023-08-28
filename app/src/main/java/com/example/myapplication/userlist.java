package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name,password,id;
    MyDBHelper DB;
    MyAdapter adapter;
    SearchView searchView;
    public  String strtype="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        searchView=findViewById(R.id.searchView);
        DB= new MyDBHelper(this);
        name=new ArrayList<>();
        password=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclelist);
        adapter= new MyAdapter(this,name,password,id);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata() {
        Cursor cursor = DB.getdata();
        if (cursor.getCount()==0){
            Toast.makeText(userlist.this,"No entry exists",Toast.LENGTH_SHORT).show();
            return;
        }else {
            while (cursor.moveToNext()){

                name.add(cursor.getString(0));
                password.add(cursor.getString(1));

            }
        }
    }
}