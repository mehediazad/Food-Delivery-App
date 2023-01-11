package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.food_app.Adapter.FavoriteAdapter;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;
import com.example.food_app.ViewModel.PopularViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavoriteList;
    private TextView textViewCartListTotalFee;
    private TextView textViewCartListDeliveryServices;
    private TextView textViewCartListTax;
    private TextView totalTxt;
    private TextView CardListCheckOutBtn;
    private TextView emptyTxt;
    private ScrollView scrollViewCartList;
    private double tax;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<Popular> popularList;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private LinearLayout profile;
    private TextView AddbtnShowDetail;
    private  ManagmentCart managmentCart;
    private Popular popular;
    private PopularViewModel popularViewModel;
    private ConstraintLayout constraintLayoutFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

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
        //CalculateCart();
        bottomNavigation();


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel.class);

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
        } else if (id == R.id.menu_favorite) {
            Intent intent = new Intent(FavoriteActivity.this, FavoriteActivity.class);
            startActivity(intent);
            finish();}
        else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(FavoriteActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(FavoriteActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(FavoriteActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(FavoriteActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(FavoriteActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(FavoriteActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(FavoriteActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
    private void initView() {
        recyclerViewFavoriteList = findViewById(R.id.recyclerViewFavoriteList);
        textViewCartListDeliveryServices = findViewById(R.id.textViewCartListDeliveryServices);
        CardListCheckOutBtn = findViewById(R.id.CardListCheckOutBtn);
        scrollViewCartList = findViewById(R.id.scrollViewCartList);
        emptyTxt = findViewById(R.id.emptyTxt);
        AddbtnShowDetail = findViewById(R.id.AddbtnShowDetail);
        constraintLayoutFavorite = findViewById(R.id.constraintLayoutFavorite);
    }
    private void setCartListAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFavoriteList.setLayoutManager(linearLayoutManager);
        favoriteAdapter = new FavoriteAdapter(managmentCart.getFavoriteCardList(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed(){}
        });
        recyclerViewFavoriteList.setAdapter(favoriteAdapter);

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, MainActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FavoriteActivity.this, ProfileActivity.class));
            }
        });
    }
    // Back to home
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
        finish();

    }
    //

}