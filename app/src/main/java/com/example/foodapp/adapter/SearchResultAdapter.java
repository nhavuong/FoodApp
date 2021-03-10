package com.example.foodapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.CategoryListActivity;
import com.example.foodapp.R;
import com.example.foodapp.SearchResultActivity;
import com.example.foodapp.model.AddingResponse;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.example.foodapp.model.SessionManagement;
import com.example.foodapp.retrofit.ApiInterface;
import com.example.foodapp.retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.myViewHolder>{
    private ArrayList<Food> searchResultList;
    private static final String TAG = "SearchResultAdapter";
    public SearchResultAdapter(ArrayList<Food> searchResultList) {
        this.searchResultList = searchResultList;
    }

    @NonNull
    @Override
    public SearchResultAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.myViewHolder holder, int position) {
        holder.curfood = searchResultList.get(position);
        final String food_name = holder.curfood.getFood_name();
        final int food_id = holder.curfood.getFood_id();
        Double food_price = holder.curfood.getFood_price();


        holder.foodName.setText(food_name);
        holder.foodPrice.setText("$" + Double.toString(food_price));
        holder.foodDescription.setText(holder.curfood.getFood_description());

    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice, foodDescription;;
        Food curfood;

        public myViewHolder(@NonNull View itemView) {
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
//                                    Toast.makeText(view.getContext(), "food added", Toast.LENGTH_SHORT).show();
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
                    SearchResultActivity.update_value();

                }
            });
        }
    }
}
