package com.example.food_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.Adapter.CategoryAdapter;
import com.example.food_app.Adapter.PopularAdapter;
import com.example.food_app.Model.Category;
import com.example.food_app.Model.Popular;
import com.example.food_app.Model.ReadWriteUserDetails;
import com.example.food_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewPopular;
    private TextView textFullName;
    private ImageView imageProfilePic;
    private CategoryAdapter categoryAdapter;
    private PopularAdapter popularAdapter;
    private List<Category> categoriesList;
    private List<Popular> popularList;
    private FloatingActionButton floatingActionButton;
    private LinearLayout homeBtn;
    private LinearLayout profile;
    private LinearLayout support;
    private LinearLayout setting;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private SearchView searchView;
    private String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarAction_bar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        //


        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        textFullName = findViewById(R.id.textFullName);
        imageProfilePic = findViewById(R.id.imageProfilePic);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });


        categoriesList = new ArrayList<>();
        popularList = new ArrayList<>();
        setCategoryAdapter();
        setPopularAdapter();
        bottomNavigation();
        setCategoryinfo();
        setPopularinfo();

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        showUserProfile(firebaseUser);

        profile = findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity2.class);
                startActivity(intent);
            }
        });
    }
// Search Bar
    private void filterList(String newText) {
        List<Popular> popularList = new ArrayList<>();
        for (Popular popular : popularList){
            if (popular.getTitle().toLowerCase().contains(newText.toLowerCase())){
               popularList.add(popular);
            }
        }
        if (popularList.isEmpty()){
            Toast.makeText(this,"No data found",Toast.LENGTH_SHORT);
        }else {
            popularAdapter.setFilterredList(popularList);
        }

    }
// End Search Bar


    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readWriteUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if (readWriteUserDetails != null) {
                    String fullname = firebaseUser.getDisplayName();

                    // set user display pic(After user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();
                    Picasso.with(MainActivity.this).load(uri).into(imageProfilePic);

                    textFullName.setText("Hi, " + fullname + "!");
                } else {
                    Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CartListActivity.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
        } else if (id == R.id.menu_favorite) {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
            finish();
        }else if (id == R.id.menu_update_profile) {
            Intent intent = new Intent(MainActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.menu_update_email) {
            Intent intent = new Intent(MainActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
//        } else if (id == R.id.menu_update_settings) {
//            Intent intent = new Intent(DeleteProfileActivity.this, UpdateSettingsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_change_password) {
            Intent intent = new Intent(MainActivity.this, PasswordChangeActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_delete_profile) {
            Intent intent = new Intent(MainActivity.this, DeleteProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_Logout) {
            authProfile.signOut();
            Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);


    }

    private void setCategoryAdapter() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoriesList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCategories.setAdapter(categoryAdapter);
    }

    private void setPopularAdapter() {
        PopularAdapter popularAdapter = new PopularAdapter(this, popularList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManager);
        recyclerViewPopular.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPopular.setAdapter(popularAdapter);

    }

    private void setCategoryinfo() {
        categoriesList.add(new Category("Pizza", "cat_1"));
        categoriesList.add(new Category("Burger", "cat_2"));
        categoriesList.add(new Category("Hotdog", "cat_3"));
        categoriesList.add(new Category("Drink", "cat_4"));
        categoriesList.add(new Category("Donut", "cat_5"));
    }

    private void setPopularinfo() {
        popularList.add(new Popular("Pepperoni pizza", "pop_1", "Slices pepperoni,Mozzerella Cheese,Fresh Orange, Ground Black Pepper,Pizza Sauce ", 400.0));
        popularList.add(new Popular("Cheese Burger", "pop_2", "Beef,Gouda Cheese, Special Sauce, Lettuce,Tomato", 350.0));
        popularList.add(new Popular("Vegetable Pizza", "pop_3", "Oliva Oil,Pitted Kalamata, Cherry tomatoes,Fresh Orange, Basil", 300.00));
    }

    // OnBack pressed exit app
    boolean twice;
    @Override
    public void onBackPressed() {
        Log.d(TAG,"Click");
        if (twice == true){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);

        }

       // super.onBackPressed();
        Toast.makeText(MainActivity.this,"Please Press Back Again to Exit",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twice = false;
                Log.d(TAG,"teice: "+twice);

            }
        },3000);
        twice = true;
    }
    //

}