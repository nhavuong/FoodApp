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
import com.example.foodapp.FoodDetails;
import com.example.foodapp.R;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.Recommended;

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
        //holder.recommendedRating.setText(recommendedList.get(position).getRating());
        //holder.recommendedCharges.setText(recommendedList.get(position).getDeliveryCharges());
        //holder.recommendedDeliveryTime.setText(recommendedList.get(position).getDeliveryTime());
        holder.recommendedPrice.setText("$ "+recommendedList.get(position).getFood_price());

        String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";

        Glide.with(context).load(BASE_URL + recommendedList.get(position).getFood_img()).into(holder.recommendedImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, FoodDetails.class);
                i.putExtra("name", recommendedList.get(position).getFood_name());
                i.putExtra("price", recommendedList.get(position).getFood_price());
                //i.putExtra("rating", recommendedList.get(position).getRating());
                i.putExtra("image", recommendedList.get(position).getFood_img());

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
        TextView recommendedName, recommendedRating, recommendedDeliveryTime, recommendedCharges, recommendedPrice;

        public RecommendedViewHolder(@NonNull View itemView) {
            super(itemView);

            recommendedImage = itemView.findViewById(R.id.recommended_image);
            recommendedName = itemView.findViewById(R.id.recommended_name);
           // recommendedRating = itemView.findViewById(R.id.recommended_rating);
            // recommendedDeliveryTime = itemView.findViewById(R.id.recommended_delivery_time);
           // recommendedCharges = itemView.findViewById(R.id.delivery_type);
           // recommendedCharges = itemView.findViewById(R.id.delivery_type);
            recommendedPrice = itemView.findViewById(R.id.recommended_price);

        }
    }


}
