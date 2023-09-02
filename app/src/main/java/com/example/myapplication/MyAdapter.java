package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private List<logindetaild> mArrayList;


    public MyAdapter(Context context, List<logindetaild> mArrayList) {
        this.context = context;
       this.mArrayList=mArrayList;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new  MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.namet.setText(mArrayList.get(position).getName());
        holder.passwordt.setText(mArrayList.get(position).getPassword());
       int i=1;
       int j= i+holder.getAbsoluteAdapterPosition();
        holder.id.setText("" + j +".");
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namet,passwordt,id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namet=itemView.findViewById(R.id.txt1);
            passwordt=itemView.findViewById(R.id.txt2);
            id=itemView.findViewById(R.id.sn);



        }
    }
    public void updateData(ArrayList<logindetaild> newData) {
        mArrayList.clear();
        mArrayList.addAll(newData);
        notifyDataSetChanged();
    }
}
