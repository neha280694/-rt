package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textviewResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textviewResult = findViewById(R.id.textviewresults);
        textviewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,login.class));

            }
        });
        Retrofit retrofit =new  Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi= retrofit.create(JsonPlaceHolderApi.class);
        Call<List<usertable>> call=jsonPlaceHolderApi.getposts();
        call.enqueue(new Callback<List<usertable>>() {
            @Override
            public void onResponse(Call<List<usertable>> call, Response<List<usertable>> response) {
                if(!response.isSuccessful()){
                    textviewResult.setText("code: "+ response.code());
                    return;
                }
                List<usertable> Usertable=response.body();
                for ( usertable usertables:Usertable){
                    String content= "";
                    content += "ID: " +usertables.getId() + "\n";
                    content += "USER ID: " +usertables.getUserid() +"\n";
                    content += "TITLE: " +usertables.getTitle() +"\n";
                    content += "TEXT" +usertables.getText() +"\n\n";
                    textviewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<usertable>> call, Throwable t) {
                textviewResult.setText(t.getMessage());

            }
        });




    }
}