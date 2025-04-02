package com.example.stockapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class II_RecyclerViewAdapter extends RecyclerView.Adapter<II_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ModelRCInventoryItem> inventoryItemModels;

    public II_RecyclerViewAdapter(Context context, ArrayList<ModelRCInventoryItem> inventoryItemModels){
        this.context = context;
        this.inventoryItemModels = inventoryItemModels;
    }

    @NonNull
    @Override
    public II_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rc_inventory_item, parent, false);
        return new II_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull II_RecyclerViewAdapter.MyViewHolder holder, int position) {

        ModelRCInventoryItem item = inventoryItemModels.get(position);

        holder.tvProductName.setText(item.getProductName());
        holder.tvProductCategory.setText(item.getProductCategory());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity())); // FIXED
        holder.tvBarcode.setText(item.getBarcode());
        holder.tvProductPrice.setText(String.valueOf(item.getProductPrice())); // FIXED
//        holder.imageView.setImageResource(inventoryItemModels.get(position).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return inventoryItemModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvProductName, tvProductCategory, tvQuantity, tvBarcode, tvProductPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvProductName = itemView.findViewById(R.id.rc_inventory_item_productName);
            tvProductCategory = itemView.findViewById(R.id.rc_inventory_item_productCategory);
            tvQuantity = itemView.findViewById(R.id.rc_inventory_item_productAmount);
            tvBarcode = itemView.findViewById(R.id.rc_inventory_item_productNumber);
            tvProductPrice = itemView.findViewById(R.id.rc_inventory_item_productPrice);
        }
    }
}
