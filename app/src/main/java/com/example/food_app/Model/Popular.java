package com.example.food_app.Model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "popular_table", indices = @Index(value = {"id"}, unique = true))

public class Popular implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String title;
    private String pic;
    private String description;
    private Double fee;
    private int numberInCard;

    public Popular(String title, String pic, String description, Double fee) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;

    }

    public void Popular_numberInCard(String title, String pic, String description, Double fee, int numberInCard) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCard = numberInCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public int getNumberInCard() {
        return numberInCard;
    }

    public void setNumberInCard(int numberInCard) {
        this.numberInCard = numberInCard;
    }
}
