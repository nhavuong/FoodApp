package com.example.foodapp.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    Food food;

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
        Food tempFood = menuList.get(position);
        holder.food = tempFood;
    }

    @Override
    public int getItemCount() { return menuList.size(); }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodDescription;

        // for adding a food.
        int cat_id, food_id, is_recommend;
        String name, description, imageUrl;
        Double price;
        Food food;

        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);

            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodDescription = itemView.findViewById(R.id.food_description);

            itemView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    food_id = food.getFood_id();
//                    name = food.getFood_name();
//                    price = food.getFood_price();
//                    imageUrl = food.getFood_img();
//                    description = food.getFood_description();
//                    cat_id = food.getCat_id();
//                    is_recommend = food.getIs_recommend();
//
//                    Food food = new Food(food_id,name,description,price,imageUrl,cat_id,is_recommend);
                    Cart.cart.add(food);
                }
            });

        }
    }

    // maybe I can just call the function by Nina, instead of making a new one here.
    // it doesn't work.
//    public void addMenuFood(View view)
//    {
//        Food food = new Food(food_id,name,description,price,imageUrl,cat_id,is_recommend);
//        Cart.cart.add(food);
//    }

//    public View getView(final int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = getLayoutInflater();
//        View row = inflater.inflate(R.layout.menu_list, parent, false);
//        Button deleteImageView = (Button) row.findViewById(R.id.DeleteImageView);
//        deleteImageView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Food food = new Food(food_id,name,description,price,imageUrl,cat_id,is_recommend);
//                Cart.cart.add(food);
//            }
//        });
//    }

}
