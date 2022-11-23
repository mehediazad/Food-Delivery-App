package com.example.food_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.food_app.Adapter.CartListAdapter;
import com.example.food_app.Adapter.CategoryAdapter;
import com.example.food_app.Adapter.PopularAdapter;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView recyclerViewCartList;
    private TextView textViewCartListTotalFee;
    private TextView textViewCartListDeliveryServices;
    private TextView textViewCartListTax;
    private TextView totalTxt;
    private TextView CardListCheckOutBtn;
    private TextView emptyTxt;
    private ScrollView scrollViewCartList;
    private double tax;
    private ManagmentCart managmentCart;
    private CartListAdapter cartListAdapter;
    private ArrayList<Popular> popularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managmentCart = new ManagmentCart(this);
        popularList = new ArrayList<>();
        initView();
        setCartListAdapter();
        CalculateCart();
        bottomNavigation();
    }

    private void initView() {
        recyclerViewCartList = findViewById(R.id.recyclerViewCartList);
        textViewCartListTotalFee = findViewById(R.id.textViewCartListTotalFee);
        textViewCartListDeliveryServices = findViewById(R.id.textViewCartListDeliveryServices);
        textViewCartListTax = findViewById(R.id.textViewCartListTax);
        totalTxt = findViewById(R.id.totalTxt);
        CardListCheckOutBtn = findViewById(R.id.CardListCheckOutBtn);
        scrollViewCartList = findViewById(R.id.scrollViewCartList);
        emptyTxt = findViewById(R.id.emptyTxt);
    }

    private void setCartListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCartList.setLayoutManager(linearLayoutManager);
        cartListAdapter = new CartListAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });
        recyclerViewCartList.setAdapter(cartListAdapter);
        if (managmentCart.getListCart().isEmpty()) {
            emptyTxt.setVisibility(View.VISIBLE);
            scrollViewCartList.setVisibility(View.GONE);
        } else {
            emptyTxt.setVisibility(View.GONE);
            scrollViewCartList.setVisibility(View.VISIBLE);
        }
    }

    private void CalculateCart() {
        double percentTax = 0.02;
        double delivery = 60;

        tax = Math.round((managmentCart.getTotalfee() * percentTax * 100) / 100);
        double total = Math.round((managmentCart.getTotalfee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managmentCart.getTotalfee() * 100) / 100;

        textViewCartListTotalFee.setText("৳" + itemTotal);
        textViewCartListTax.setText("৳" + tax);
        textViewCartListDeliveryServices.setText("৳" + delivery);
        totalTxt.setText("৳" + total);
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

}