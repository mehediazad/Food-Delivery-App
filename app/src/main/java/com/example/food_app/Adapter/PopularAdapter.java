package com.example.food_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app.Activity.ShowDetailActivity;
import com.example.food_app.Model.Popular;
import com.example.food_app.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private Context context;
    private List<Popular> popularList;

    public PopularAdapter(Context context, List<Popular> popularList) {
        this.context = context;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new PopularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Popular popular = popularList.get(position);

        holder.textViewTitlePopular.setText(popular.getTitle());
        holder.textViewPopularFee.setText(String.valueOf(popular.getFee()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularList.get(position)
                .getPic(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.imageViewPopular);

        holder.addBtnPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", popularList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitlePopular;
        ImageView imageViewPopular;
        TextView textViewPopularFee;
        TextView addBtnPopular;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitlePopular = itemView.findViewById(R.id.textViewTitlePopular);
            imageViewPopular = itemView.findViewById(R.id.imageViewPopular);
            textViewPopularFee = itemView.findViewById(R.id.textViewPopularFee);
            addBtnPopular = itemView.findViewById(R.id.addBtnPopular);

        }
    }
}
