package com.example.foodapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Food;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.myViewHolder>{
    private ArrayList<Food> searchResultList;
    private static final String TAG = "SearchResultAdapter";
    public SearchResultAdapter(ArrayList<Food> searchResultList) {
        this.searchResultList = searchResultList;
    }

    @NonNull
    @Override
    public SearchResultAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.myViewHolder holder, int position) {
        final Food food = searchResultList.get(position);
        final String food_name = food.getFood_name();
        final int food_id = food.getFood_id();
        Double food_price = food.getFood_price();

        holder.foodNametv.setText(food_name);
        holder.foodPricetv.setText(Double. toString(food_price));

        holder.addbtnTocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Add to cart" + food_id + food_name);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        Food food;
        private TextView foodNametv;
        private TextView foodPricetv;
        private Button addbtnTocart;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            this.foodNametv = itemView.findViewById(R.id.foodNametv);
            this.foodPricetv = itemView.findViewById(R.id.foodPricetv);
            this.addbtnTocart = itemView.findViewById(R.id.addButton);
        }
    }
}
