package com.example.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Food;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Food> foodItem;
    private Context context;

    public CartAdapter(List<Food> foodItem, Context context) {
        this.foodItem = foodItem;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, final int position) {
        holder.cartName.setText(foodItem.get(position).getFood_name());
        holder.cartPrice.setText("$ " + foodItem.get(position).getFood_price());
        //holder.cartQty.setText(foodItem.get(position).get());
    }

    @Override
    public int getItemCount() { return foodItem.size(); }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cartName, cartPrice, cartQty;

        public CartViewHolder(@NonNull View itemView)
        {
            super(itemView);

            cartName = itemView.findViewById(R.id.foodNametv);
            cartPrice = itemView.findViewById(R.id.foodPricetv);
        }
    }

}
