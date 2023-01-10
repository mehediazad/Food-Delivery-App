package com.example.food_app.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "shoppingAddress_table", indices = @Index(value = {"id"}, unique = true))
public class ShippingAddress implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Shipping_name")
    public String name;

    @ColumnInfo(name = "Shipping_phone")
    public String phone;

    @ColumnInfo(name = "Shipping_address")
    public String address;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
