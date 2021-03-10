package com.example.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.CartListActivity;
import com.example.foodapp.R;
import com.example.foodapp.model.AddingResponse;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.DeletingResponse;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.cartQty.setText(String.valueOf(foodItem.get(position).getQuantity()));
        holder.curfood = foodItem.get(position);
    }

    @Override
    public int getItemCount() { return foodItem.size(); }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        Food curfood;
        TextView cartName, cartPrice, cartQty;

        public CartViewHolder(@NonNull final View itemView)
        {
            super(itemView);

            cartName = itemView.findViewById(R.id.foodNametv);
            cartPrice = itemView.findViewById(R.id.foodPricetv);
            cartQty = itemView.findViewById(R.id.foodQty);

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
                                    cartQty.setText(String.valueOf(curfood.getQuantity()));

                                    TextView totalPriceTv = itemView.getRootView().findViewById(R.id.total_price);
                                    totalPriceTv.setText("$ " + Cart.getTotalPrice());
                                    TextView totalTaxTv = itemView.getRootView().findViewById(R.id.tax);
                                    totalTaxTv.setText("$ " + Cart.getTotalTax());

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

                }
            });


            itemView.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    SessionManagement sessionManagement = new SessionManagement(view.getContext());
                    int userId = sessionManagement.getSession();
                    if(userId != -1){
                        Call<DeletingResponse> call = RetrofitClient.getRetrofitInstance().create(ApiInterface.class).deleteFromCart(userId, curfood.getFood_id());
                        call.enqueue(new Callback<DeletingResponse>() {
                            @Override
                            public void onResponse(Call<DeletingResponse> call, Response<DeletingResponse> response) {
                                if(response.isSuccessful()){

                                    if(curfood.getQuantity() == 1 && Cart.amount == 1)
                                    {
                                        Cart.cart.remove(curfood);
                                        curfood.setQuantity(0);
                                        Cart.amount = 0;
                                    }
                                    else if (curfood.getQuantity() == 1 && Cart.amount > 1) {
                                        Cart.cart.remove(curfood);
                                        curfood.setQuantity(0);
                                        Cart.amount --;
                                    }
                                    else if (Cart.amount > 0)
                                    {
                                        for (Food f : Cart.cart) {
                                            if (f.getFood_name().equals(curfood.getFood_name())) {
                                                f.setQuantity(f.getQuantity() - 1);
                                                Cart.amount--;
                                                break;
                                            }
                                        }
                                    }

                                    cartQty.setText(String.valueOf(curfood.getQuantity()));

                                    TextView totalPriceTv = itemView.getRootView().findViewById(R.id.total_price);
                                    totalPriceTv.setText("$ " + Cart.getTotalPrice());
                                    TextView totalTaxTv = itemView.getRootView().findViewById(R.id.tax);
                                    totalTaxTv.setText("$ " + Cart.getTotalTax());

                                    Toast.makeText(view.getContext(), "food deleted", Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onFailure(Call<DeletingResponse> call, Throwable t) {
                                Toast.makeText(view.getContext(), "food not deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(view.getContext(), "need to login", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

}
