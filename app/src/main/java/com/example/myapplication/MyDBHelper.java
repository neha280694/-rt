package com.example.myapplication;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {



    public MyDBHelper( Context context) {
        super(context, "userdata.db", null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Userdetails(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,name TEXT primary key, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("DROP TABLE IF EXISTS userdetails");

    }
    public  boolean addContact(String name,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",name);
        values.put("password",password);
        long result = DB.insert("userdetails",null,values);
        if(result==-1){
          return false;
        }else {
            return true;

        }

    }
    public Cursor getdata()
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userdetails", null);
        return cursor;
    }
}
