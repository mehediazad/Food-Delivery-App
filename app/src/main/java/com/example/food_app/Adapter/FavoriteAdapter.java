package com.example.food_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private ArrayList<Popular> popularList;
    private ChangeNumberItemsListener changeNumberItemsListener;
    public ManagmentCart managmentCart;
    public Popular popular;
    public RecyclerView recyclerViewFavoriteList;

    public FavoriteAdapter(ArrayList<Popular> popularList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.popularList = popularList;
        // this.managmentFavoriteCart = new ManagmentFavoriteCart(context);
        this.managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favorite, parent, false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Popular popular = popularList.get(position);
        holder.textViewTitleCartList.setText(popular.getTitle());
        holder.feeEachItemCaetList.setText(String.valueOf(popular.getFee()));
        int drawableReourceId = holder.itemView.getContext().getResources().getIdentifier(popular.getPic(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.imageViewCartList);

        holder.AddbtnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.insertPopularFood(popular);
                holder.constraintLayoutFavorite.setVisibility(View.GONE);
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCartList;
        TextView textViewTitleCartList;
        TextView feeEachItemCaetList;
        TextView AddbtnShowDetail;
        ConstraintLayout constraintLayoutFavorite;
        TextView emptyTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCartList = itemView.findViewById(R.id.imageViewCartList);
            textViewTitleCartList = itemView.findViewById(R.id.textViewTitleCartList);
            feeEachItemCaetList = itemView.findViewById(R.id.feeEachItemCaetList);
            AddbtnShowDetail = itemView.findViewById(R.id.AddbtnShowDetail);
            constraintLayoutFavorite = itemView.findViewById(R.id.constraintLayoutFavorite);
            emptyTxt = itemView.findViewById(R.id.emptyTxt);
        }
    }
}
