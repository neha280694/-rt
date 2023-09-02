package com.example.myapplication;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {



    public MyDBHelper( Context context) {
        super(context, "userdata.db", null, 2);


    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
       // DB.execSQL("create Table logindetaild(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,name TEXT primary key, password TEXT)");
        String createTableQuery = "CREATE TABLE logindetaild ("
                + "id INTEGER,"
                + "name TEXT,"
                + "password TEXT,"
                + "PRIMARY KEY (id, name)"
                + ")";
        DB.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("DROP TABLE IF EXISTS logindetaild");

    }
    public  boolean addContact(String name,String password){

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",name);
        values.put("password",password);
        long result = DB.insert("logindetaild",null,values);
        if(result==-1){
          return false;
        }else {
            return true;

        }


    }
    public  List<logindetaild>getdetail(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String [] sqlselect={"id","name","password"};
        String tablename="logindetaild";
        qb.setTables(tablename);
        Cursor cursor=qb.query(db,sqlselect,null,null,null,null,null);
        List<logindetaild> detail =new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
               logindetaild Logindetald =new logindetaild();

               Logindetald.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
               Logindetald.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                detail.add(Logindetald);
            }while (cursor.moveToNext());
        }
        return detail;
    }

    public Cursor getdata()
    {
        SQLiteDatabase DB =this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from logindetaild", null);
        return cursor;
    }
    public boolean checkCredentials(String name, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"name"}; // You can customize this based on your needs
        String selection = "name = ? AND password = ?";
        String[] selectionArgs = {name, password};

        Cursor cursor = db.query("logindetaild", columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isValid;
    }

}
