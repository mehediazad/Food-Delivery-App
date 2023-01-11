package com.example.food_app.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.food_app.Model.ShippingAddress;
import com.example.food_app.Repository.ShippingRepository;

import java.util.List;

public class ShipppingViewModel extends AndroidViewModel {

    public ShippingRepository repository;
    public LiveData<List<ShippingAddress>> getAllShippingAddress;

    public ShipppingViewModel(@NonNull Application application) {
        super(application);

        repository = new ShippingRepository(application);
        getAllShippingAddress = repository.getAllShippingAddress;
    }
    public void insertShipping(ShippingAddress shippingAddress) {
        repository.insertShipping(shippingAddress);
    }
    public void updateShipping(ShippingAddress shippingAddress){
        repository.updateShipping(shippingAddress);
    }
}
