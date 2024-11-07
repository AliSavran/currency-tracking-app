package com.alisavran.retrofitjava.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alisavran.retrofitjava.R;
import com.alisavran.retrofitjava.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<CryptoModel> cyrptoList;
    private String [] colors = {"#0d929a","#45818e","#396878","#213145","#38383a"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cyrptoList) {
        this.cyrptoList = cyrptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(cyrptoList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cyrptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);

        }

        public void bind(CryptoModel cryptoModel, String[] colors, Integer position){

            itemView.setBackgroundColor(Color.parseColor(colors[position % 5]));
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);

            textName.setText(cryptoModel.currency);
            textPrice.setText(cryptoModel.price);

        }
    }

}
