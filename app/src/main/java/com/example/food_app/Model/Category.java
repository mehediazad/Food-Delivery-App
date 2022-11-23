package com.example.food_app.Model;

import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "note_table", indices = @Index(value = {"id"}, unique = true))
public class Category {
    private String title;
    private String pic;

    public Category(String title, String pic) {
        this.title = title;
        this.pic = pic;
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
}
