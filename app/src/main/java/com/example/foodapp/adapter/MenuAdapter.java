package com.example.foodapp.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.model.AddingResponse;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.curfood = menuList.get(position);
    }

    @Override
    public int getItemCount() { return menuList.size(); }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodDescription;

        // for adding a food.
        int cat_id, food_id, is_recommend;
        String name, description, imageUrl;
        Double price;
        Food curfood;

        public MenuViewHolder(@NonNull View itemView)
        {
            super(itemView);

            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.food_price);
            foodDescription = itemView.findViewById(R.id.food_description);

            itemView.findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    SessionManagement sessionManagement = new SessionManagement(view.getContext());
                    int userId = sessionManagement.getSession();
                    if(userId != -1){
                        // logged in
                        boolean existed = false;
                        for (Food f :
                                Cart.cart) {
                            if (f.getFood_name().equals(curfood.getFood_name())){
                                existed = true;
                                f.setQuantity(f.getQuantity()+1);
                                Cart.amount++;
                                break;
                            }
                        }
                        if(!existed){
                            Food food = new Food(curfood.getFood_id(),name,description,price,imageUrl,cat_id,is_recommend, 1);
                            Cart.cart.add(food);
                            Cart.amount++;
                        }

                        Call<AddingResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).addToCart(userId, food_id);
                        call.enqueue(new Callback<AddingResponse>() {
                            @Override
                            public void onResponse(Call<AddingResponse> call, Response<AddingResponse> response) {
                                Toast.makeText(view.getContext(), "food added", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<AddingResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), "food not added", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(view.getContext(), "need to login", Toast.LENGTH_LONG).show();
                    }
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
