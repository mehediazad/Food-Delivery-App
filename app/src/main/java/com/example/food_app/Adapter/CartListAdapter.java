package com.example.food_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Helper.ManagmentCart;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.Viewholder> {
    private ArrayList<Popular> popularList;
    private ManagmentCart managmentCart;
    private ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<Popular> popularList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.popularList = popularList;
        this.managmentCart = new ManagmentCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cardlist, parent, false);
        return new CartListAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        Popular popular = popularList.get(position);

        holder.textViewTitleCartList.setText(popular.getTitle());
        holder.feeEachItemCaetList.setText(String.valueOf(popular.getFee()));
        holder.totalFeeEachItemCaetList.setText(String.valueOf(Math.round(popular.getNumberInCard() * popular.getFee() * 100) / 100));
        holder.numberOfItemCartList.setText(String.valueOf(popular.getNumberInCard()));

        int drawableReourceId = holder.itemView.getContext().getResources().getIdentifier(popular.getPic(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableReourceId)
                .into(holder.imageViewCartList);

        holder.imageViewCartListPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.plusNumberOfFood(popularList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
        holder.imageViewCartListMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.minusNumberofFood(popularList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imageViewCartList;
        TextView textViewTitleCartList;
        ImageView imageViewCartListMinusBtn;
        ImageView imageViewCartListPlusBtn;
        TextView numberOfItemCartList;
        TextView feeEachItemCaetList;
        TextView totalFeeEachItemCaetList;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewCartList = itemView.findViewById(R.id.imageViewCartList);
            textViewTitleCartList = itemView.findViewById(R.id.textViewTitleCartList);
            imageViewCartListMinusBtn = itemView.findViewById(R.id.imageViewCartListMinusBtn);
            imageViewCartListPlusBtn = itemView.findViewById(R.id.imageViewCartListPlusBtn);
            numberOfItemCartList = itemView.findViewById(R.id.numberOfItemCartList);
            feeEachItemCaetList = itemView.findViewById(R.id.feeEachItemCaetList);
            totalFeeEachItemCaetList = itemView.findViewById(R.id.totalFeeEachItemCaetList);
        }
    }
}
