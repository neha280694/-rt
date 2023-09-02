package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<String> ld=new ArrayList<>();
    private List<logindetaild> ldd;
    MyDBHelper DB;
    MyAdapter adapter;
    MyAdapter adp;
    MyAdapter adtp;
    SearchView searchView;
    public  String strtype="";
    private List<logindetaild> listDetail= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        searchView=findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setQueryHint("Search by first letter...");
        ldd=new ArrayList<>();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<logindetaild> filteredList = new ArrayList<>();

                ldd = DB.getdetail();

                for (int i = 0; i < ldd.size(); i++) {
                    // Check if the name contains the query (case-insensitive)
                    if (ldd.get(i).getName().toUpperCase().contains(query.toUpperCase())) {
                        filteredList.add(ldd.get(i));
                    }
                }

                // Create and set the adapter with the filtered data
                adp = new MyAdapter(userlist.this, filteredList);
                recyclerView.setAdapter(adp);
                recyclerView.setLayoutManager(new LinearLayoutManager(userlist.this));

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Implement if you want to perform filtering as the user types
                newText = newText.toLowerCase(); // Ensure case-insensitive filtering
                ArrayList<logindetaild> filteredData = new ArrayList<>();

                if (newText.isEmpty()) {
                    // If the query is empty, show all data
                    filteredData.addAll(ldd);
                } else {
                    char firstChar = newText.charAt(0);

                    for (logindetaild item : ldd) {
                        if (Character.toLowerCase(item.getName().charAt(0)) == Character.toLowerCase(firstChar)) {
                            filteredData.add(item); // Add items matching the first character
                        }
                    }
                }

                // Create a new adapter with the filtered data
                adtp = new MyAdapter(userlist.this, filteredData);

                // Set the adapter for the RecyclerView
                recyclerView.setAdapter(adtp);
                recyclerView.setLayoutManager(new LinearLayoutManager(userlist.this));

                return false;
            }
        });

        DB= new MyDBHelper(this);

        recyclerView=findViewById(R.id.recyclelist);
        adapter= new MyAdapter(this,DB.getdetail());
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

                ld.add(cursor.getString(0));
                ld.add(cursor.getString(1));

            }
        }
    }
}