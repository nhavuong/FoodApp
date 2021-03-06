package com.example.foodapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.FoodDetailsActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.Food;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder> {

    private Context context;
    private List<Food> recommendedList;

    public RecommendedAdapter(Context context, List<Food> recommendedList) {
        this.context = context;
        this.recommendedList = recommendedList;
    }

    @NonNull
    @Override
    public RecommendedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recommended_recycler_items, parent, false);
        return new RecommendedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedViewHolder holder, final int position) {

        holder.recommendedName.setText(recommendedList.get(position).getFood_name());
        holder.recommendedPrice.setText("$ "+recommendedList.get(position).getFood_price());

        Glide.with(context).load(RetrofitClient.BASE_URL + recommendedList.get(position).getFood_img()).into(holder.recommendedImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetailsActivity.class);
                i.putExtra("food_id", recommendedList.get(position).getFood_id());
                i.putExtra("food_name", recommendedList.get(position).getFood_name());
                i.putExtra("food_price", recommendedList.get(position).getFood_price());
                i.putExtra("food_img", recommendedList.get(position).getFood_img());
                i.putExtra("food_description", recommendedList.get(position).getFood_description());
                i.putExtra("cat_id", recommendedList.get(position).getCat_id());
                i.putExtra("is_recommend", recommendedList.get(position).getIs_recommend());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommendedList.size();
    }

    public static class RecommendedViewHolder extends RecyclerView.ViewHolder{

        ImageView recommendedImage;
        TextView recommendedName, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);

        }
    }


}
