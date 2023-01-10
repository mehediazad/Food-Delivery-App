package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.food_app.Model.ShippingAddress;
import com.example.food_app.R;
import com.example.food_app.ViewModel.ShipppingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShippingActivity extends AppCompatActivity {
    private EditText editText_ShippingAddress_mobile;
    private TextView button_Shipping;
    private EditText editText_Shopping_full_name;
    private EditText editText_ShippingAddress;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ShipppingViewModel shipppingViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        editText_ShippingAddress_mobile = findViewById(R.id.editText_ShippingAddress_mobile);
        button_Shipping = findViewById(R.id.button_Shipping);
        editText_Shopping_full_name = findViewById(R.id.editText_Shopping_full_name);
        editText_ShippingAddress = findViewById(R.id.editText_ShippingAddress);
        progressBar = findViewById(R.id.progressBar);

        shipppingViewModel = ViewModelProviders.of(this).get(ShipppingViewModel.class);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //
        bottomNavigation();

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        button_Shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFullName = editText_Shopping_full_name.getText().toString();
                String textMobile = editText_ShippingAddress_mobile.getText().toString();
                String textAddress = editText_ShippingAddress.getText().toString();

                // Validate Mobile Number using Matcher and patten (Regular Expression)
                String mobileRegex = "(01)[3-9][0-9]{8}"; // First no. can be {01} and rest 11 num, can be any number;
                Matcher mobileMatcher;
                Pattern mobilePatten = Pattern.compile(mobileRegex);
                mobileMatcher = mobilePatten.matcher(textMobile);

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(ShippingActivity.this, "Please Enter Your Full Name", Toast.LENGTH_SHORT).show();
                    editText_Shopping_full_name.setError("Full Name is required");
                    editText_Shopping_full_name.requestFocus();

                }
                else if ((TextUtils.isEmpty(textMobile))) {
                    Toast.makeText(ShippingActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    editText_ShippingAddress_mobile.setError("Phone Number is required");
                    editText_ShippingAddress_mobile.requestFocus();
                } else if (textMobile.length() != 11) {
                    Toast.makeText(ShippingActivity.this, "Please re-enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    editText_ShippingAddress_mobile.setError("Phone Number should be 11 digits");
                    editText_ShippingAddress_mobile.requestFocus();
                }else if (!mobileMatcher.find()){
                    Toast.makeText(ShippingActivity.this, "Please re-enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    editText_ShippingAddress_mobile.setError("Phone Number is not valid");
                    editText_ShippingAddress_mobile.requestFocus();
                }
                else if (TextUtils.isEmpty(textAddress)){
                    Toast.makeText(ShippingActivity.this, "Please Enter Your Shipping Address", Toast.LENGTH_SHORT).show();
                    editText_ShippingAddress.setError("Shipping Address is required");
                    editText_ShippingAddress.requestFocus();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    shippingAddress(textFullName,textMobile,textAddress);
                }
            }
        });

    }

    private void shippingAddress(String textFullName, String textMobile, String textAddress) {
        ShippingAddress shippingAddress = new ShippingAddress();

        shippingAddress.name = textFullName;
        shippingAddress.phone = textMobile;
        shippingAddress.address = textAddress;

        shipppingViewModel.insertShipping(shippingAddress);

        Toast.makeText(this,"Successfully!",Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

        Intent intent = new Intent(ShippingActivity.this,PaymentActivity.class);
        intent.putExtra("Name",textFullName);
        intent.putExtra("Phone",textMobile);
        intent.putExtra("Address",textAddress);
        startActivity(intent);
        finish();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profile = findViewById(R.id.profile);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShippingActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShippingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShippingActivity.this, ProfileActivity.class);
                startActivity(intent);
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
            Intent intent = new Intent(ShippingActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(ShippingActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(ShippingActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(ShippingActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(ShippingActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ShippingActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(ShippingActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

}