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
                        Call<AddingResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).addToCart(userId, curfood.getFood_id());
                        call.enqueue(new Callback<AddingResponse>() {
                            @Override
                            public void onResponse(Call<AddingResponse> call, Response<AddingResponse> response) {
                                if(response.isSuccessful()){
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
                                        curfood.setQuantity(1);
                                        Cart.cart.add(curfood);
                                        Cart.amount++;
                                    }
                                    Toast.makeText(view.getContext(), "food added", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<AddingResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), "food not added", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(view.getContext(), "need to login", Toast.LENGTH_SHORT).show();
                    }

                    // update the number of food in the menu list.
//                    MenuViewHolder.super.itemView.
//                            itemCount.setText(String.valueOf(Cart.amount));

                }
            });

        }
    }
}
