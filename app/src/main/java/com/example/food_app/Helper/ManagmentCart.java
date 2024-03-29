package com.example.food_app.Helper;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Adapter.PopularAdapter;
import com.example.food_app.Database.PopularDatabase;
import com.example.food_app.Interface.ChangeNumberItemsListener;
import com.example.food_app.Model.Popular;
import com.example.food_app.ViewModel.PopularViewModel;

import java.util.ArrayList;
import java.util.List;

public class ManagmentCart {
    public Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertPopularFood(Popular item) {
        ArrayList<Popular> popularList = getListCart();
        boolean existAlready = false;

        int n = 0;
        for (int i = 0; i < popularList.size(); i++) {
            if (popularList.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }

        }
        if (existAlready) {
            popularList.get(n).setNumberInCard(item.getNumberInCard());
        } else {
            popularList.add(item);
        }
        tinyDB.putListObject("CardList", popularList);
        Toast.makeText(context, "Added to Your Card", Toast.LENGTH_SHORT).show();
    }

    public void insertFavoriteFood(Popular item) {
        ArrayList<Popular> popularList = getFavoriteCardList();
        boolean existAlready = false;

        int n = 0;
        for (int i = 0; i < popularList.size(); i++) {
            if (popularList.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }

        }
        if (existAlready) {
            popularList.get(n).setNumberInCard(item.getNumberInCard());
        } else {
            popularList.add(item);
        }
        tinyDB.putListObject("FavoriteCardList", popularList);
        Toast.makeText(context, "Added to Your Favorite Card", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Popular> getListCart() {
        return tinyDB.getListObject("CardList");
   }

    public ArrayList<Popular> getFavoriteCardList() {
        return tinyDB.getListObject("FavoriteCardList");
    }

    public void plusNumberOfFood(ArrayList<Popular> popularList, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        popularList.get(position).setNumberInCard(popularList.get(position).getNumberInCard() + 1);
        tinyDB.putListObject("CardList", popularList);
        changeNumberItemsListener.changed();
    }

    public void minusNumberofFood(ArrayList<Popular> popularList, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (popularList.get(position).getNumberInCard() == 1) {
            popularList.remove(position);
        } else {
            popularList.get(position).setNumberInCard(popularList.get(position).getNumberInCard() - 1);
        }
        tinyDB.putListObject("CardList", popularList);
        changeNumberItemsListener.changed();
    }

    public Double getTotalfee() {
        ArrayList<Popular> popularList = getListCart();
        double fee = 0;
        for (int i = 0; i <popularList.size() ; i++) {
            fee = fee + (popularList.get(i).getFee() * popularList.get(i).getNumberInCard());
        }
        return fee;
    }
}
