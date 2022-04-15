package com.example.groceryproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryproject.Data.Info;

import java.util.ArrayList;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    Context context;
    ArrayList<Info> list;

    public NewAdapter(Context context, ArrayList<Info> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.info_output,parent,false);
        return new NewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {
        Info info = list.get(position);
        holder.date.setText(info.getDate());
        holder.text.setText(info.getText());
        holder.amount.setText(Integer.toString(info.getAmount()));
        holder.price.setText(Double.toString(info.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{
        TextView date,text, amount, price;
        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.outputDate);
            text = itemView.findViewById(R.id.outputText);
            amount = itemView.findViewById(R.id.outputAmount);
            price = itemView.findViewById(R.id.outputPrice);
        }
    }
}
