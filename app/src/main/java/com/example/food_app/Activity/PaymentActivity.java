package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.Model.ShippingAddress;
import com.example.food_app.R;
import com.example.food_app.ViewModel.ShipppingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private TextView textView_payment_name;
    private TextView textView_payment_phone;
    private TextView textView_payment_address;
    private ImageView imageView_right_arrow;
    private ShipppingViewModel shipppingViewModel;
    private ShippingAddress shippingAddress;
    private List<ShippingAddress> shippingAddressList;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    String textFullName;
    String textMobile;
    String textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        textView_payment_name = findViewById(R.id.textView_payment_name);
        textView_payment_phone = findViewById(R.id.textView_payment_phone);
        textView_payment_address = findViewById(R.id.textView_payment_address);
        imageView_right_arrow = findViewById(R.id.imageView_right_arrow);



        shippingAddressList = new ArrayList<>();
        shipppingViewModel = ViewModelProviders.of(this).get(ShipppingViewModel.class);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();


        Intent intent = getIntent();

        String fullName = intent.getStringExtra("Name");
        String mobile = intent.getStringExtra("Phone");
        String address = intent.getStringExtra("Address");

        textView_payment_name.setText(fullName);
        textView_payment_phone.setText(mobile);
        textView_payment_address.setText(address);


        imageView_right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this,UpdateShippingAddressActivity.class);
                intent.putExtra("Name",fullName);
                intent.putExtra("Phone",mobile);
                intent.putExtra("Address",address);
                startActivity(intent);
                finish();
            }
        });
    // Update
        Intent intent1 = getIntent();

        String updateFullName = intent1.getStringExtra("Name");
        String updateMobile = intent1.getStringExtra("Phone");
        String updateAddress = intent1.getStringExtra("Address");

        textView_payment_name.setText(updateFullName);
        textView_payment_phone.setText(updateMobile);
        textView_payment_address.setText(updateAddress);

        //
        bottomNavigation();

    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profile = findViewById(R.id.profile);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PaymentActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, ProfileActivity.class);
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
            Intent intent = new Intent(PaymentActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(PaymentActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(PaymentActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(PaymentActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(PaymentActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PaymentActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(PaymentActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

}