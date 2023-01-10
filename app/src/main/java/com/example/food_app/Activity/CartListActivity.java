package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.Adapter.CartListAdapter;
import com.example.food_app.Adapter.CategoryAdapter;
import com.example.food_app.Adapter.PopularAdapter;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;
import com.example.food_app.ViewModel.PopularViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private LinearLayout profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);


        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //

        profile = findViewById(R.id.profile);

        managmentCart = new ManagmentCart(this);
        popularList = new ArrayList<>();
        initView();
        setCartListAdapter();
        CalculateCart();
        bottomNavigation();

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        CardListCheckOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartListActivity.this,ShippingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_refresh) {
            startActivity(getIntent());
            finish();
            overridePendingTransition(0, 0);
        } else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(CartListActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(CartListActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(CartListActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(CartListActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(CartListActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CartListActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(CartListActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


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

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartListActivity.this, ProfileActivity.class));
            }
        });
    }

}