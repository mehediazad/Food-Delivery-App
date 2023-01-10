package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;
import com.example.food_app.ViewModel.PopularViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.security.PublicKey;

public class ShowDetailActivity extends AppCompatActivity {
    private TextView textViewTitleShowDetail;
    private TextView textViewTkShowDetail;
    private ImageView imageViewShowDetail;
    private ImageView btnMinusShowDetail;
    private ImageView btnPlusShowDetail;
    private TextView textViewnumberOfOrder;
    private TextView textViewDescriptionShowDetail;
    private TextView AddbtnShowDetail;
    private ManagmentCart managmentCart;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private TextView button_login;
    private PopularViewModel popularViewModel;

    private Popular object;
    int numberOfOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        popularViewModel = ViewModelProviders.of(this).get(PopularViewModel.class);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //

        managmentCart = new ManagmentCart(this);

        initView();
        getBundle();
        bottomNavigation();
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profile = findViewById(R.id.profile);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowDetailActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowDetailActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getBundle() {
        object = (Popular) getIntent().getSerializableExtra("object");
        int drawableResourceId = this.getResources().getIdentifier(object.getPic(), "drawable", this.getPackageName());
        Glide.with(this)
                .load(drawableResourceId)
                .into(imageViewShowDetail);
        textViewTitleShowDetail.setText(object.getTitle());
        textViewTkShowDetail.setText("৳" + object.getFee());
        textViewDescriptionShowDetail.setText(object.getDescription());
        textViewnumberOfOrder.setText(String.valueOf(numberOfOrder));

        btnPlusShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfOrder = numberOfOrder + 1;
                textViewnumberOfOrder.setText(String.valueOf(numberOfOrder));

            }
        });

        btnMinusShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfOrder > 1) {
                    numberOfOrder = numberOfOrder - 1;
                }
                textViewnumberOfOrder.setText(String.valueOf(numberOfOrder));
            }
        });
        AddbtnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object.setNumberInCard(numberOfOrder);
                managmentCart.insertPopularFood(object);
                popularViewModel.insertPopular(object);
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
            Intent intent = new Intent(ShowDetailActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(ShowDetailActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(ShowDetailActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(ShowDetailActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(ShowDetailActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ShowDetailActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(ShowDetailActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

    private void initView() {
        textViewTitleShowDetail = findViewById(R.id.textViewTitleShowDetail);
        textViewTkShowDetail = findViewById(R.id.textViewTkShowDetail);
        imageViewShowDetail = findViewById(R.id.imageViewShowDetail);
        btnMinusShowDetail = findViewById(R.id.btnMinusShowDetail);
        btnPlusShowDetail = findViewById(R.id.btnPlusShowDetail);
        textViewnumberOfOrder = findViewById(R.id.textViewnumberOfOrder);
        textViewDescriptionShowDetail = findViewById(R.id.textViewDescriptionShowDetail);
        AddbtnShowDetail = findViewById(R.id.AddbtnShowDetail);
        button_login = findViewById(R.id.button_login);

    }
}