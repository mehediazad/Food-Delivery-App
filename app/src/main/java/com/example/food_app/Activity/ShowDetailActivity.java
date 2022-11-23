package com.example.food_app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;

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

    private Popular object;
    int numberOfOrder = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        managmentCart = new ManagmentCart(this);


        initView();
        getBundle();
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
            }
        });


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

    }
}