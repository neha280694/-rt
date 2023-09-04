package com.example.myapplication;

import static okhttp3.internal.Util.filterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<String> ld = new ArrayList<>();
    private List<logindetaild> ldd;
    MyDBHelper DB;
    MyAdapter adapter;
    MyAdapter adp;
    MyAdapter adtp;
    SearchView searchView;
    public String strtype = "";
    private List<logindetaild> listDetail = new ArrayList<>();
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setQuery("", false);
        searchView.setQueryHint("Search");

        ldd = new ArrayList<>();

        SearchView searchView = findViewById(R.id.searchView); // Change to your SearchView id




      /*  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

            //@Override
           *//* public boolean onQueryTextChange(String newText) {
                // Implement if you want to perform filtering as the user types
                ArrayList<logindetaild> filteredData = new ArrayList<>();


                if (newText.length() > 0) { // Check if newText is not empty
                    char firstChar = Character.toLowerCase(newText.charAt(0));

                    for (logindetaild item : ldd) {
                        if (item.getName().length() > 0 && item.getName().charAt(0) == firstChar) {
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
            }*//*
           public boolean onQueryTextChange(String newText) {
                // Implement filtering as the user types
                ArrayList<logindetaild> filteredData = new ArrayList<>();
                boolean queryMatchesData = false; // Assume it doesn't match by default

                if (newText.length() > 0) { // Check if newText is not empty
                    String query = newText.toLowerCase();

                    for (logindetaild item : ldd) {
                        String name = item.getName().toLowerCase();
                        if (name.startsWith(query)) {
                            filteredData.add(item); // Add items matching the query
                            queryMatchesData = true; // Set the flag to true if there's a match
                        }
                    }
                }


                // Create a new adapter with the filtered data
                adtp = new MyAdapter(userlist.this, filteredData);

                // Set the adapter for the RecyclerView
                recyclerView.setAdapter(adtp);
                recyclerView.setLayoutManager(new LinearLayoutManager(userlist.this));

                if (!queryMatchesData && newText.length() > 0) {
                    // Show a message in a popup dialog when no items match the query
                    MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance("No data available by this name.");
                    messageDialogFragment.show(getSupportFragmentManager(), "MessageDialog");
                } else {
                    // Dismiss any previously shown message dialogs
                    Fragment messageDialogFragment = getSupportFragmentManager().findFragmentByTag("MessageDialog");
                    if (messageDialogFragment != null) {
                        getSupportFragmentManager().beginTransaction().remove(messageDialogFragment).commit();
                    }
                }


                return false;
            }

        });*/
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayList<logindetaild> filteredList = filterData(query);
                updateRecyclerView(filteredList, query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<logindetaild> filteredList = filterData(newText);
                updateRecyclerView(filteredList, newText);
                return true;
            }
        });


        DB = new MyDBHelper(this);

        recyclerView = findViewById(R.id.recyclelist);
        adapter = new MyAdapter(this, DB.getdetail());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata() {
        Cursor cursor = DB.getdata();
        if (cursor.getCount() == 0) {
            Toast.makeText(userlist.this, "No entry exists", Toast.LENGTH_SHORT).show();
            return;

        } else {
            while (cursor.moveToNext()) {

                ld.add(cursor.getString(0));
                ld.add(cursor.getString(1));

            }
        }
    }

    private ArrayList<logindetaild> filterData(String query) {
        ArrayList<logindetaild> filteredData = new ArrayList<>();

        ldd = DB.getdetail();

        for (int i = 0; i < ldd.size(); i++) {
            // Check if the name contains the query (case-insensitive)
            if (ldd.get(i).getName().toUpperCase().contains(query.toUpperCase())) {
                filteredData.add(ldd.get(i));
            }
        }

        return filteredData;
    }

    private void updateRecyclerView(ArrayList<logindetaild> filteredList, String query) {
        // Create and set the adapter with the filtered data
        adp = new MyAdapter(userlist.this, filteredList);
        recyclerView.setAdapter(adp);
        recyclerView.setLayoutManager(new LinearLayoutManager(userlist.this));

        if (filteredList.isEmpty() && !query.isEmpty()) {
            // Show a message in a popup dialog when no items match the query
            MessageDialogFragment messageDialogFragment = MessageDialogFragment.newInstance("No data available by this name.");
            messageDialogFragment.show(getSupportFragmentManager(), "MessageDialog");
        } else {
            // Dismiss any previously shown message dialogs
            Fragment messageDialogFragment = getSupportFragmentManager().findFragmentByTag("MessageDialog");
            if (messageDialogFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(messageDialogFragment).commit();
            }
        }
    }
}

