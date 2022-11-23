package com.example.food_app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.example.food_app.Adapter.CategoryAdapter;
import com.example.food_app.Adapter.PopularAdapter;
import com.example.food_app.Model.Category;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategories;
    private RecyclerView recyclerViewPopular;
    private CategoryAdapter categoryAdapter;
    private PopularAdapter popularAdapter;
    private List<Category> categoriesList;
    private List<Popular> popularList;
    private FloatingActionButton floatingActionButton;
    private LinearLayout homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategories = findViewById(R.id.recyclerViewCategories);
        recyclerViewPopular = findViewById(R.id.recyclerViewPopular);
        categoriesList = new ArrayList<>();
        popularList = new ArrayList<>();
        setCategoryAdapter();
        setPopularAdapter();
        bottomNavigation();
        setCategoryinfo();
        setPopularinfo();
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
}