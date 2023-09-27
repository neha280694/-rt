package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptercurrency extends RecyclerView.Adapter<Adaptercurrency.MyViewHolder> {
    private Context context;
    private List<currencyDetail> mArrayList;

    public Adaptercurrency(Context context, List<currencyDetail> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;


    }

    @NonNull
    @Override
    public Adaptercurrency.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currenctitem, parent, false);
        return new Adaptercurrency.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptercurrency.MyViewHolder holder, int position) {
        currencyDetail currencyItem = mArrayList.get(position);
        holder.txtc.setText(mArrayList.get(position).getCurrency());
        holder.txtd.setText(String.valueOf(mArrayList.get(position).getConvertedValue()));
        if (holder.txtc.getText().toString().equals("USD")){
            holder.txtd.setText("$" +String.valueOf(mArrayList.get(position).getConvertedValue()));

        }
        if (holder.txtc.getText().toString().equals("POUND")){
            holder.txtd.setText("£" +String.valueOf(mArrayList.get(position).getConvertedValue()));

        }
        if (holder.txtc.getText().toString().equals("YEN")){
            holder.txtd.setText("¥" +String.valueOf(mArrayList.get(position).getConvertedValue()));

        }
        if (holder.txtc.getText().toString().equals("EURO")){
            holder.txtd.setText("€" +String.valueOf(mArrayList.get(position).getConvertedValue()));

        }

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtc, txtd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtc = itemView.findViewById(R.id.txtc);
            txtd = itemView.findViewById(R.id.txtd);
        }
    }

    public void setFilteredList(List<currencyDetail> filteredList) {
        mArrayList = filteredList;
        notifyDataSetChanged();
    }
}
