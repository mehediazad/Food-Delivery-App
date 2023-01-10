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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.Model.ShippingAddress;
import com.example.food_app.R;
import com.example.food_app.ViewModel.ShipppingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UpdateShippingAddressActivity extends AppCompatActivity {
    private EditText editText_UpdateShopping_full_name;
    private EditText editText_UpdateShippingAddress_mobile;
    private EditText editText_UpdateShippingAddress;
    private TextView button_UpdateShipping;

    private ShipppingViewModel shipppingViewModel;
    private ShippingAddress shippingAddress;
    private List<ShippingAddress> shippingAddressList;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_shipping_address);

        editText_UpdateShopping_full_name = findViewById(R.id.editText_UpdateShopping_full_name);
        editText_UpdateShippingAddress_mobile = findViewById(R.id.editText_UpdateShippingAddress_mobile);
        editText_UpdateShippingAddress = findViewById(R.id.editText_UpdateShippingAddress);
        button_UpdateShipping = findViewById(R.id.button_UpdateShipping);

        shipppingViewModel = ViewModelProviders.of(this).get(ShipppingViewModel.class);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //

        // get data Payment and set
        Intent intent = getIntent();
        String fullName = intent.getStringExtra("Name").toString().trim();
        String mobile = intent.getStringExtra("Phone").toString().trim();
        String address = intent.getStringExtra("Address").toString().trim();

        editText_UpdateShopping_full_name.setText(fullName);
        editText_UpdateShippingAddress_mobile.setText(mobile);
        editText_UpdateShippingAddress.setText(address);
        //



        button_UpdateShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateFullName = editText_UpdateShopping_full_name.getText().toString();
                String updateMobile = editText_UpdateShippingAddress_mobile.getText().toString();
                String updateAddress = editText_UpdateShippingAddress.getText().toString();
                
                UpdateShippingAddress(updateFullName,updateMobile,updateAddress);
            }
        });


    }

    private void UpdateShippingAddress(String updateFullName, String updateMobile, String updateAddress) {

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.name = updateFullName;
        shippingAddress.phone = updateMobile;
        shippingAddress.address = updateAddress;

        shipppingViewModel.updateShipping(shippingAddress);
        Toast.makeText(this,"Update Successful",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(UpdateShippingAddressActivity.this,PaymentActivity.class);

        intent.putExtra("Name",updateFullName);
        intent.putExtra("Phone",updateMobile);
        intent.putExtra("Address",updateAddress);

        startActivity(intent);
        finish();
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
            Intent intent = new Intent(UpdateShippingAddressActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(UpdateShippingAddressActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(UpdateShippingAddressActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(UpdateShippingAddressActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(UpdateShippingAddressActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateShippingAddressActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(UpdateShippingAddressActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateShippingAddressActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateShippingAddressActivity.this, MainActivity.class));
            }
        });
    }
}