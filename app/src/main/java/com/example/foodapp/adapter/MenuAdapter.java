package com.example.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{

    private List<Food> menuList;
    private Context context;

    // for adding a food.
    int cat_id, food_id, is_recommend;
    String name, description, imageUrl;
    Double price;

    public MenuAdapter(List<Food> foodItem, Context context) {
        this.menuList = foodItem;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_list, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.foodName.setText(menuList.get(position).getFood_name());
        holder.foodPrice.setText("$ " + menuList.get(position).getFood_price());
        holder.foodDescription.setText(menuList.get(position).getFood_description());

        food_id = menuList.get(position).getFood_id();
        name = menuList.get(position).getFood_name();
        price = menuList.get(position).getFood_price();
        imageUrl = menuList.get(position).getFood_img();
        description = menuList.get(position).getFood_description();
        cat_id = menuList.get(position).getCat_id();
        is_recommend = menuList.get(position).getIs_recommend();

    }

    @Override
    public int getItemCount() { return menuList.size(); }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodDescription;



        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);

            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodDescription = itemView.findViewById(R.id.food_description);

        }
    }

    // maybe I can just call the function by Nina, instead of making a new one here.
    public void addFood(View view)
    {
        Food food = new Food(food_id,name,description,price,imageUrl,cat_id,is_recommend);
        Cart.cart.add(food);
    }

}
