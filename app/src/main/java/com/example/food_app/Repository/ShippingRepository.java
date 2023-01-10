package com.example.food_app.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.food_app.Database.ShippingDao;
import com.example.food_app.Database.ShippingDatabase;
import com.example.food_app.Model.Popular;
import com.example.food_app.Model.ShippingAddress;

import java.util.List;

public class ShippingRepository {
    public ShippingDao shippingDao;
    public LiveData<List<ShippingAddress>> getAllShippingAddress;

    public ShippingRepository(Application application) {
        ShippingDatabase database = ShippingDatabase.getInstance(application);
        shippingDao = database.shippingDao();
        getAllShippingAddress = shippingDao.getAllShippingAddress();
    }
    public  void insertShipping(ShippingAddress shippingAddress){
        shippingDao.insertShipping(shippingAddress);
    }
    public void updateShipping(ShippingAddress shippingAddress){
        shippingDao.updateShipping(shippingAddress);
    }

}
