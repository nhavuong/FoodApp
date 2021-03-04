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
import com.example.foodapp.CategoryListActivity;
import com.example.foodapp.FoodDetailsActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.Category;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    // try Food type.
//    private Context context;
//    private List<Food> categoryList;
//
//    public CategoryAdapter(Context context, List<Food> categoryList) {
//        this.context = context;
//        this.categoryList = categoryList;
//    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        View view = LayoutInflater.from(context).inflate(R.layout.new_popular_recycler_items, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.popular_recycler_items, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {

        holder.categoryName.setText(categoryList.get(position).getCat_name());
        String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";
        String BASE_URL2 = "http://foodapp-env.eba-idm3cpsj.us-east-2.elasticbeanstalk.com/";

        Glide.with(context).load(RetrofitClient.BASE_URL + categoryList.get(position).getCat_img()).into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, FoodDetailsActivity.class);
                Intent i = new Intent(context, CategoryListActivity.class);
//                i.putExtra("name", categoryList.get(position).getCat_name());
               // i.putExtra("price", categoryList.get(position).getPrice());
                //i.putExtra("rating", categoryList.get(position).getRating());
//                i.putExtra("image", categoryList.get(position).getCat_img());
                i.putExtra("cat_id", categoryList.get(position).getCat_id());

                context.startActivity(i);
            }
        });

        // try Food type

//        // information needed to bind with the recycler view in MainActivity.
////        // wierd here. Food name (say Spring Roll) into category name (say Appetizer), doesn't make sense at all.
////        // But, this is just giving string to the recycler view. there shouldn't be any problem...
//        holder.categoryName.setText(categoryList.get(position).getFood_name());
////        holder.categoryName.setText("Appetizer");
//        String BASE_URL = "http://foodordering-env.eba-smutnzic.us-east-2.elasticbeanstalk.com/";
//        String BASE_URL2 = "http://foodapp-env.eba-idm3cpsj.us-east-2.elasticbeanstalk.com/";
//
////        Glide.with(context).load(RetrofitClient.BASE_URL + categoryList.get(position).getFood_img()).into(holder.categoryImage);
//
//        // setting next activity, not checked. Not related to the view of MainActivity.
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i = new Intent(context, FoodDetailsActivity.class);
//                // new activity for the menu list.
//                Intent i = new Intent(context, CategoryActivity.class);
//
//                i.putExtra("name", categoryList.get(position).getFood_name());
//                // i.putExtra("price", categoryList.get(position).getPrice());
//                //i.putExtra("rating", categoryList.get(position).getRating());
////                i.putExtra("image", categoryList.get(position).getFood_img());
//
//                // some more needed information for next acitivity.
//                i.putExtra("price", categoryList.get(position).getFood_price());
//                i.putExtra("description", categoryList.get(position).getFood_description());
//                i.putExtra("cat_id", categoryList.get(position).getCat_id());
//
//                // not sure about need this data or not.
//                i.putExtra("food_id", categoryList.get(position).getFood_id());
//
//                context.startActivity(i);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            // this is for the layout in MainActivity, so it should be fine.
            categoryName = itemView.findViewById(R.id.all_menu_name);
            categoryImage = itemView.findViewById(R.id.all_menu_image);

        }
    }
}
