package com.example.maket.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "feed_back")
public class FeedBack {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "star")
    private Double star;

    public FeedBack(String content, Double star) {
        this.content = content;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getStar() {
        return star;
    }

    public void setStar(Double star) {
        this.star = star;
    }
}
