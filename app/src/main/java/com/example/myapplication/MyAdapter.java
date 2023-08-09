package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList namet,passwordt;

    public MyAdapter(Context context, ArrayList namet, ArrayList passwordt) {
        this.context = context;
        this.namet = namet;
        this.passwordt = passwordt;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new  MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namet.setText((String.valueOf(namet.get(position))));
        holder.passwordt.setText((String.valueOf(passwordt.get(position))));

    }

    @Override
    public int getItemCount() {
        return namet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namet,passwordt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namet=itemView.findViewById(R.id.txt1);
            passwordt=itemView.findViewById(R.id.txt2);
        }
    }
}
