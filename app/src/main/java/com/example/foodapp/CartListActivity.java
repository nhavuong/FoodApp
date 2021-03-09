package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodapp.adapter.CartAdapter;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.Food;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

public class CartListActivity extends AppCompatActivity {

    private static final String TAG = "CARTLISTACTIVITY";

    RecyclerView recycle_cart;
    TextView total_price, tax_price;
    Button btn_order;

    CartAdapter cartAdapter;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        recycle_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        total_price = findViewById(R.id.total_price);
        tax_price = findViewById(R.id.tax);
        btn_order = findViewById(R.id.btn_order);

        recycle_cart.setAdapter(cartAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        total_price.setText("$ " + Cart.getTotalPrice());
        tax_price.setText("$ " + Cart.getTotalTax());
        populateCartList(Cart.cart);

    }

    private void populateCartList(List<Food> foodList){
        recycle_cart = findViewById(R.id.recycler_cart);
        cartAdapter = new CartAdapter(foodList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycle_cart.setLayoutManager(layoutManager);
        recycle_cart.setAdapter(cartAdapter);

        recycle_cart.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

    }


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AZzIokUiGGTxL9RVse9ipFywdDNS4ux9h_d1RcddAsS6uf9qFdko3Vn8ut7GKsnVkMXKBUcuPd9xziA5");

    public void beginPayment(View view){
        Intent serviceConfig = new Intent(this, PayPalService.class);
        serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(serviceConfig);

        System.out.println("Total = " + Cart.getTotalPrice());
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(Cart.getTotalPrice())), "USD", "Total:", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentConfig = new Intent(this, PaymentActivity.class);
        paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config); //send the same configuration for restart resiliency
        paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentConfig, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("foodApp", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                } catch (JSONException e) {
                    Log.e("foodApp", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("foodApp", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("foodApp", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }



}